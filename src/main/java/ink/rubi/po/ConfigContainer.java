package ink.rubi.po;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ink.rubi.json.ConfigSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Rubi
 * @version : 2019-05-19 12:31 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigContainer {
    @JsonSerialize(using = ConfigSerialize.GlobalConfigSerializer.class)
    @JsonDeserialize(using = ConfigSerialize.GlobalConfigDeserializer.class)
    private GlobalConfig globalConfig;
    @JsonSerialize(using = ConfigSerialize.DataSourceConfigSerializer.class)
    @JsonDeserialize(using = ConfigSerialize.DataSourceConfigDeserializer.class)
    private DataSourceConfig dataSourceConfig;
    @JsonSerialize(using = ConfigSerialize.StrategyConfigSerializer.class)
    @JsonDeserialize(using = ConfigSerialize.StrategyConfigDeserializer.class)
    private StrategyConfig strategyConfig;
    @JsonDeserialize(using = ConfigSerialize.PackageConfigDeserializer.class)
    @JsonSerialize(using = ConfigSerialize.PackageConfigSerializer.class)
    private PackageConfig packageConfig;

}
