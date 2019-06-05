package ink.rubi.codegenerator.po.holder;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Rubi
 * @version : 2019-06-04 22:53 下午
 */
@Data
@Accessors(chain = true)
public class StrategyConfigHolder implements ConfigHolder<StrategyConfig> {
    /**
     * 是否大写命名
     */
    private boolean isCapitalMode = false;
    /**
     * 是否跳过视图
     */
    private boolean skipView = false;
    /**
     * 名称转换
     */
    private INameConvert nameConvert;
    /**
     * 数据库表映射到实体的命名策略
     */
    private String naming = "no_change";
    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定按照 naming 执行</p>
     */
    private String columnNaming = null;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 字段前缀
     */
    private String fieldPrefix;
    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superEntityClass;
    /**
     * 自定义基础的Entity类，公共字段
     */
    private String superEntityColumns;
    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
    /**
     * 自定义继承的Service类全称，带包名
     */
    private String superServiceClass = ConstVal.SUPER_SERVICE_CLASS;
    /**
     * 自定义继承的ServiceImpl类全称，带包名
     */
    private String superServiceImplClass = ConstVal.SUPER_SERVICE_IMPL_CLASS;
    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superControllerClass;
    /**
     * 需要包含的表名，允许正则表达式（与exclude二选一配置）
     */
    private String include = null;
    /**
     * 需要排除的表名，允许正则表达式
     */
    private String exclude = null;
    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean entitySerialVersionUID = true;
    /**
     * 【实体】是否生成字段常量（默认 false）<br>
     * -----------------------------------<br>
     * public static final String ID = "test_id";
     */
    private boolean entityColumnConstant = false;
    /**
     * 【实体】是否为构建者模型（默认 false）<br>
     * -----------------------------------<br>
     * public User setName(String name) { this.name = name; return this; }
     */
    private boolean entityBuilderModel = false;
    /**
     * 【实体】是否为lombok模型（默认 false）<br>
     * <a href="https://projectlombok.org/">document</a>
     */
    private boolean entityLombokModel = false;
    /**
     * Boolean类型字段是否移除is前缀（默认 false）<br>
     * 比如 : 数据库字段名称 : 'is_xxx',类型为 : tinyint. 在映射实体的时候则会去掉is,在实体类中映射最终结果为 xxx
     */
    private boolean entityBooleanColumnRemoveIsPrefix = false;
    /**
     * 生成 <code>@RestController</code> 控制器
     * <pre>
     *      <code>@Controller</code> -> <code>@RestController</code>
     * </pre>
     */
    private boolean restControllerStyle = false;
    /**
     * 驼峰转连字符
     * <pre>
     *      <code>@RequestMapping("/managerUserActionHistory")</code> -> <code>@RequestMapping("/manager-user-action-history")</code>
     * </pre>
     */
    private boolean controllerMappingHyphenStyle = false;
    /**
     * 是否生成实体时，生成字段注解
     */
    private boolean entityTableFieldAnnotationEnable = false;
    /**
     * 乐观锁属性名称
     */
    private String versionFieldName;
    /**
     * 逻辑删除属性名称
     */
    private String logicDeleteFieldName;
    /**
     * 表填充字段
     */
    private Map<String, String> tableFillList = null;


    @Override
    public StrategyConfig convert() {
        return new StrategyConfig()
                .setCapitalMode(this.isCapitalMode)
                .setSkipView(this.skipView)

                .setNameConvert(this.nameConvert)
                .setNaming(NamingStrategy.valueOf(this.naming))
                .setColumnNaming(NamingStrategy.valueOf(this.columnNaming))

                .setTablePrefix(format(this.tablePrefix))
                .setFieldPrefix(format(this.fieldPrefix))
                .setSuperEntityColumns(format(this.superEntityColumns))
                .setInclude(format(this.include))
//                .setExclude(this.exclude)

                .setSuperEntityClass(this.superEntityClass)
                .setSuperMapperClass(this.superMapperClass)
                .setSuperServiceClass(this.superServiceClass)
                .setSuperServiceImplClass(this.superServiceImplClass)
                .setSuperControllerClass(this.superControllerClass)

                .setEntitySerialVersionUID(this.entitySerialVersionUID)
                .setEntityColumnConstant(this.entityColumnConstant)
                .setEntityBuilderModel(this.entityBuilderModel)
                .setEntityLombokModel(this.entityLombokModel)
                .setEntityBooleanColumnRemoveIsPrefix(this.entityBooleanColumnRemoveIsPrefix)
                .setRestControllerStyle(this.restControllerStyle)
                .setEntityTableFieldAnnotationEnable(this.entityTableFieldAnnotationEnable)
                .setVersionFieldName(this.versionFieldName)
                .setLogicDeleteFieldName(this.logicDeleteFieldName)

                .setTableFillList(convertToList(this.tableFillList));
    }

    public static List<TableFill> convertToList(Map<String, String> tableFillList) {
        if (tableFillList == null) return null;
        List<TableFill> list = new ArrayList<>();
        tableFillList.forEach((name, type) -> list.add(new TableFill(name, FieldFill.valueOf(type))));
        return list;

    }
}
