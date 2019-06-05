package ink.rubi.coffee.po.holder;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author : Rubi
 * @version : 2019-06-04 23:30 下午
 */
@Data
@Accessors(chain = true)
public class PackageConfigHolder implements ConfigHolder<PackageConfig>{
    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "com.baomidou";
    /**
     * 父包模块名
     */
    private String moduleName = null;
    /**
     * Entity包名
     */
    private String entity = "entity";
    /**
     * Service包名
     */
    private String service = "service";
    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    /**
     * Mapper包名
     */
    private String mapper = "mapper";
    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";
    /**
     * Controller包名
     */
    private String controller = "controller";
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

//    /**
//     * 父包名
//     */
//    public String getParent() {
//        if (StringUtils.isNotEmpty(moduleName)) {
//            return parent + StringPool.DOT + moduleName;
//        }
//        return parent;
//    }
    @Override
    public PackageConfig convert() {
        return new PackageConfig()

                .setParent(this.parent)
                .setModuleName(this.moduleName)

                .setEntity(this.entity)
                .setService(this.service)
                .setServiceImpl(this.serviceImpl)
                .setMapper(this.mapper)
                .setXml(this.xml)
                .setController(this.controller)

                .setPathInfo(this.pathInfo);
    }
}
