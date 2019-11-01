package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.generator.config.TemplateConfig

/**
 * @author Rubi
 * @since 2019-06-05 15:43
 */

data class TemplateConfigHolder(
        var entity: String? = null,
        var entityKt: String? = null,
        var service: String? = null,
        var serviceImpl: String? = null,
        var mapper: String? = null,
        var xml: String? = null,
        var controller: String? = null,
        var engine: String? = null) : ConfigHolder<TemplateConfig> {


    override fun convert(): TemplateConfig {
        return TemplateConfig()
                .setEntity(this.entity)
                .setEntityKt(this.entityKt)
                .setServiceImpl(this.serviceImpl)
                .setService(this.service)
                .setMapper(this.mapper)
                .setXml(this.xml)
                .setController(this.controller)
    }


}
