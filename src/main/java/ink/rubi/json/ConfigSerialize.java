package ink.rubi.json;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import ink.rubi.controller.MainController;
import ink.rubi.po.ConfigContainer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author : Rubi
 * @version : 2019-05-19 03:58 上午
 */
@Slf4j
public class ConfigSerialize {
    public static class DataSourceConfigSerializer extends JsonSerializer<DataSourceConfig> {
        @Override
        public void serialize(DataSourceConfig value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            writeNormalField(value, gen, serializers);
            gen.writeEndObject();
        }
    }

    public static class GlobalConfigSerializer extends JsonSerializer<GlobalConfig> {
        @Override
        public void serialize(GlobalConfig value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            writeNormalField(value, gen, serializers);
            gen.writeEndObject();
        }
    }

    public static class PackageConfigSerializer extends JsonSerializer<PackageConfig> {
        @Override
        public void serialize(PackageConfig value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            writeNormalField(value, gen, serializers);
            gen.writeEndObject();
        }
    }

    public static class StrategyConfigSerializer extends JsonSerializer<StrategyConfig> {
        @Override
        public void serialize(StrategyConfig value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            writeNormalField(value, gen, serializers);
//            writeArrayField(value, gen, serializers);
            gen.writeEndObject();
        }
    }


    public static class PackageConfigDeserializer extends JsonDeserializer<PackageConfig> {

        @Override
        public PackageConfig deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            PackageConfig packageConfig = new PackageConfig();
            JsonNode root = p.getCodec().readTree(p);
            readNormalField(packageConfig, root, p, ctxt);
            return packageConfig;
        }
    }

    public static class DataSourceConfigDeserializer extends JsonDeserializer<DataSourceConfig> {
        @Override
        public DataSourceConfig deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode root = p.getCodec().readTree(p);
            JsonNode dbQueryNode = root.get("dbQuery");
            JsonNode typeConvertNode = root.get("typeConvert");
            JsonNode dbTypeNode = root.get("dbType");
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            readNormalField(dataSourceConfig, root, p, ctxt);
            try {
                IDbQuery dbQuery = (IDbQuery) Class.forName(dbQueryNode.asText()).newInstance();
                ITypeConvert typeConvert = (ITypeConvert) Class.forName(typeConvertNode.asText()).newInstance();
                DbType dbType = DbType.valueOf(dbTypeNode.asText());
                dataSourceConfig.setDbQuery(dbQuery)
                        .setDbType(dbType)
                        .setTypeConvert(typeConvert);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                log.error("{0}", e);
            }

            return dataSourceConfig;
        }
    }

    public static class GlobalConfigDeserializer extends JsonDeserializer<GlobalConfig> {
        @Override
        public GlobalConfig deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode root = p.getCodec().readTree(p);
            JsonNode idType = root.get("idType");
            GlobalConfig globalConfig = new GlobalConfig();
            readNormalField(globalConfig, root, p, ctxt);
            return globalConfig
                    .setIdType(IdType.valueOf(idType.asText()));

        }
    }


    public static class StrategyConfigDeserializer extends JsonDeserializer<StrategyConfig> {

        @Override
        public StrategyConfig deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            StrategyConfig strategyConfig = new StrategyConfig();
            JsonNode root = p.getCodec().readTree(p);
            JsonNode namingNode = root.get("naming");
            JsonNode columnNamingNode = root.get("columnNaming");
            NamingStrategy naming = NamingStrategy.valueOf(namingNode.asText());
            NamingStrategy columnNaming = NamingStrategy.valueOf(columnNamingNode.asText());
            strategyConfig.setNaming(naming).setColumnNaming(columnNaming);
            readNormalField(strategyConfig, root, p, ctxt);
            return strategyConfig;
        }
    }


    private static boolean isPrimitiveOrString(Field field) {
        Class<?> type = field.getType();
        return type.isAssignableFrom(String.class) || type.isPrimitive();
    }

    private static void readNormalField(Object obj, JsonNode root, JsonParser p, DeserializationContext ctxt) {
        Stream.of(obj.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            if (isPrimitiveOrString(field) || field.getType().isArray()  ) {
                try {
                    field.set(obj, getSuitableResult(root.get(field.getName())));
                } catch (IllegalAccessException e) {
                    log.error("{0}", e);
                }
            }
        });
    }

    private static Object getSuitableResult(JsonNode value) {
        if (value == null) {
            return null;
        }
        if (value.isTextual()) {
            return value.asText();
        } else if (value.isArray()) {
            //careful Other Array todo
            Iterator<JsonNode> iterator = value.iterator();
            List<String> strs = new ArrayList<>();
            while (iterator.hasNext()) {
                strs.add(iterator.next().asText());
            }
            Object stringArr = Array.newInstance(String.class,strs.size());
            for (int i = 0; i < strs.size(); i++) {
                Array.set(stringArr,i,strs.get(i));
            }
            return stringArr;
        } else if (value.isNull()) {
            return null;
        } else if (value.isBoolean()) {
            return value.asBoolean();
        } else if (value.isShort()) {
            return value.shortValue();
        } else if (value.isInt()) {
            return value.intValue();
        } else if (value.isLong()) {
            return value.longValue();
        } else if (value.isFloat()) {
            return value.floatValue();
        } else if (value.isDouble()) {
            return value.doubleValue();
        } else if (value.isBinary()) {
            try {
                return value.binaryValue();
            } catch (IOException e) {
                log.error("{0}", e);
            }
        }
        return null;
    }


    private static void writeNormalField(Object obj, JsonGenerator gen, SerializerProvider serializers) {
        Stream.of(obj.getClass().getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                Object fieldVal = field.get(obj);
                boolean valueNotNull = fieldVal != null;
                String fieldName = field.getName();
                Object resultVal = null;
                //careful Other Array todo
                if (valueNotNull && fieldVal.getClass().isArray()) {
                    String[] arr = (String[]) fieldVal;
                    gen.writeArrayFieldStart(fieldName);
                    Stream.of(arr).forEach(str -> {
                        try {
                            gen.writeString(str);
                        } catch (IOException e) {
                            log.error("{0}", e);
                        }
                    });
                    gen.writeEndArray();
                } else {
                    if (isPrimitiveOrString(field)) {
                        resultVal = fieldVal;
                    } else if (valueNotNull && fieldVal.getClass().isEnum()) {
                        resultVal = ((Enum) fieldVal).name();
                    } else if (valueNotNull) {
                        resultVal = fieldVal.getClass().getName();
                    }
                    gen.writeObjectField(fieldName, resultVal);
                }
            } catch (IOException | IllegalAccessException e) {
                log.error("{0}", e);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ConfigContainer container = MainController.getObjectMapper()
                .readValue(new File("C:\\Users\\13447\\Desktop\\config.json"), ConfigContainer.class);
        System.out.println(container.getStrategyConfig());
    }


}
