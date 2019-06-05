package ink.rubi.coffee.po.holder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : Rubi
 * @version : 2019-06-04 23:21 下午
 */
@Data
@Accessors(chain = true)
public class GlobalConfigHolder implements ConfigHolder<GlobalConfig> {


    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String outputDir = "D://";

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;

    /**
     * 是否打开输出目录
     */
    private boolean open = true;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache = false;

    /**
     * 开发人员
     */
    private String author;

    /**
     * 开启 Kotlin 模式
     */
    private boolean kotlin = false;

    /**
     * 开启 swagger2 模式
     */
    private boolean swagger2 = false;

    /**
     * 开启 ActiveRecord 模式
     */
    private boolean activeRecord = false;

    /**
     * 开启 BaseResultMap
     */
    private boolean baseResultMap = false;

    /**
     * 时间类型对应策略
     */
    private String dateType = "TIME_PACK";

    /**
     * 开启 baseColumnList
     */
    private boolean baseColumnList = false;
    /**
     * 各层文件名称方式，例如： %sAction 生成 UserAction
     * %s 为占位符
     */
    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    /**
     * 指定生成的主键的ID类型
     */
    private String idType;


    @Override
    public GlobalConfig convert() {
        return new GlobalConfig()
                .setOutputDir(this.outputDir)
                .setFileOverride(this.fileOverride)
                .setOpen(this.open)
                .setEnableCache(this.enableCache)
                .setAuthor(this.author)
                .setKotlin(this.kotlin)
                .setSwagger2(this.swagger2)
                .setActiveRecord(this.activeRecord)
                .setBaseResultMap(this.baseResultMap)

                .setDateType(DateType.valueOf(this.dateType))

                .setBaseColumnList(this.baseColumnList)
                .setEntityName(this.entityName)
                .setMapperName(this.mapperName)
                .setXmlName(this.xmlName)
                .setServiceName(this.serviceName)
                .setServiceImplName(this.serviceImplName)
                .setControllerName(this.controllerName)

                .setIdType(IdType.valueOf(this.idType));
    }
}
