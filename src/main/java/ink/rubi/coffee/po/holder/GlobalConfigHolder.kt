package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.generator.config.GlobalConfig
import com.baomidou.mybatisplus.generator.config.rules.DateType

/**
 * @author : Rubi
 * @version : 2019-06-04 23:21 下午
 */
data class GlobalConfigHolder(var outputDir: String = "D://",
                              var isFileOverride: Boolean = false,
                              var isOpen: Boolean = true,
                              var isEnableCache: Boolean = false,
                              var author: String? = null,
                              var isKotlin: Boolean = false,
                              var isSwagger2: Boolean = false,
                              var isActiveRecord: Boolean = false,
                              var isBaseResultMap: Boolean = false,
                              var dateType: String = "TIME_PACK",
                              var isBaseColumnList: Boolean = false,
                              var entityName: String? = null,
                              var mapperName: String? = null,
                              var xmlName: String? = null,
                              var serviceName: String? = null,
                              var controllerName: String? = null,
                              var serviceImplName: String? = null,
                              var idType: String? = null
) : ConfigHolder<GlobalConfig> {
    override fun convert(): GlobalConfig {
        return GlobalConfig()
                .setOutputDir(this.outputDir)
                .setFileOverride(this.isFileOverride)
                .setOpen(this.isOpen)
                .setEnableCache(this.isEnableCache)
                .setAuthor(this.author)
                .setKotlin(this.isKotlin)
                .setSwagger2(this.isSwagger2)
                .setActiveRecord(this.isActiveRecord)
                .setBaseResultMap(this.isBaseResultMap)
                .setDateType(DateType.valueOf(this.dateType))
                .setBaseColumnList(this.isBaseColumnList)
                .setEntityName(this.entityName)
                .setMapperName(this.mapperName)
                .setXmlName(this.xmlName)
                .setServiceName(this.serviceName)
                .setServiceImplName(this.serviceImplName)
                .setControllerName(this.controllerName)
                .setIdType(IdType.valueOf(this.idType!!))
    }
}
