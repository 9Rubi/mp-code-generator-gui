package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.generator.config.PackageConfig

/**
 * @author : Rubi
 * @version : 2019-06-04 23:30 下午
 */

class PackageConfigHolder(var parent: String = "com.baomidou",
                          var moduleName: String? = null,
                          var entity: String = "entity",
                          var service: String = "service",
                          var serviceImpl: String = "service.impl",
                          var mapper: String = "mapper",
                          var xml: String = "mapper.xml",
                          var controller: String = "controller",
                          var pathInfo: Map<String, String>? = null) : ConfigHolder<PackageConfig> {

    override fun convert(): PackageConfig {
        return PackageConfig()
                .setParent(this.parent)
                .setModuleName(this.moduleName)
                .setEntity(this.entity)
                .setService(this.service)
                .setServiceImpl(this.serviceImpl)
                .setMapper(this.mapper)
                .setXml(this.xml)
                .setController(this.controller)
                .setPathInfo(this.pathInfo)
    }
}
