package ink.rubi.coffee.po.holder;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Rubi
 * @since 2019-06-05 15:43
 */
@Data
@Accessors(chain = true)
public class TemplateConfigHolder implements ConfigHolder<TemplateConfig> {

    private String entity;

    private String entityKt;

    private String service;

    private String serviceImpl;

    private String mapper;

    private String xml;

    private String controller;

    private String engine;


    @Override
    public TemplateConfig convert() {
        return new TemplateConfig()
                .setEntity(this.entity)
                .setEntityKt(this.entityKt)
                .setServiceImpl(this.serviceImpl)
                .setService(this.service)
                .setMapper(this.mapper)
                .setXml(this.xml)
                .setController(this.controller);
    }


}
