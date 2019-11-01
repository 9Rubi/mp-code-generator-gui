package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.generator.config.DataSourceConfig

/**
 * @author : Rubi
 * @version : 2019-06-04 22:39 下午
 */
data class DataSourceConfigHolder(var schemaName: String? = null, var url: String? = null,
                                  var driverName: String? = null, var username: String? = null,
                                  var password: String? = null) : ConfigHolder<DataSourceConfig> {
    override fun convert(): DataSourceConfig {
        return DataSourceConfig()
                .setSchemaName(this.schemaName)
                .setUrl(this.url)
                .setDriverName(this.driverName)
                .setUsername(this.username)
                .setPassword(this.password)
    }

}
