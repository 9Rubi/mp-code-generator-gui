package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.generator.config.*
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine

/**
 * @author : Rubi
 * @version : 2019-06-05 00:46 上午
 */
data class AllConfig(var globalConfig: GlobalConfig? = null, var dataSourceConfig: DataSourceConfig? = null,
                     var strategyConfig: StrategyConfig? = null, var packageConfig: PackageConfig? = null,
                     var templateConfig: TemplateConfig? = null, var templateEngine: AbstractTemplateEngine? = null)
