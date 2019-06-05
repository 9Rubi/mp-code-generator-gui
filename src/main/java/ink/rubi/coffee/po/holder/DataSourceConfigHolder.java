package ink.rubi.coffee.po.holder;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : Rubi
 * @version : 2019-06-04 22:39 下午
 */
@Data
@Accessors(chain = true)
public class DataSourceConfigHolder implements ConfigHolder<DataSourceConfig> {

    /**
     * PostgreSQL schemaName
     */
    private String schemaName;
    /**
     * 驱动连接的URL
     */
    private String url;
    /**
     * 驱动名称
     */
    private String driverName;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;

    @Override
    public DataSourceConfig convert() {
        return new DataSourceConfig()
                .setSchemaName(this.schemaName)
                .setUrl(this.url)
                .setDriverName(this.driverName)
                .setUsername(this.username)
                .setPassword(this.password);
    }

}
