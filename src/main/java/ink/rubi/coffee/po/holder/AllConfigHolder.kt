package ink.rubi.coffee.po.holder

import ink.rubi.coffee.constant.Engine

/**
 * @author : Rubi
 * @version : 2019-05-19 12:31 下午
 */

data class AllConfigHolder(var globalConfigHolder: GlobalConfigHolder? = null, var dataSourceConfigHolder: DataSourceConfigHolder? = null,
                           var strategyConfigHolder: StrategyConfigHolder? = null, var packageConfigHolder: PackageConfigHolder? = null,
                           var templateConfigHolder: TemplateConfigHolder? = null) : ConfigHolder<AllConfig> {
    override fun convert(): AllConfig {
        return AllConfig(
                this.globalConfigHolder?.convert(),
                this.dataSourceConfigHolder?.convert(),
                this.strategyConfigHolder?.convert(),
                this.packageConfigHolder?.convert(),
                this.templateConfigHolder?.convert(),
                Engine.getEngine(this.templateConfigHolder!!.engine!!)
        )
    }
}