package ink.rubi.codegenerator.po.holder;

import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : Rubi
 * @version : 2019-06-05 00:46 上午
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AllConfig {
    private GlobalConfig globalConfig;
    private DataSourceConfig dataSourceConfig;
    private StrategyConfig strategyConfig;
    private PackageConfig packageConfig;
    private TemplateConfig templateConfig;
    private AbstractTemplateEngine templateEngine;
}
