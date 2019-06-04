package ink.rubi.codegenerator.po.holder;

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
public class AllConfigHolder implements ConfigHolder<AllConfig> {
    private GlobalConfigHolder globalConfigHolder;
    private DataSourceConfigHolder dataSourceConfigHolder;
    private StrategyConfigHolder strategyConfigHolder;
    private PackageConfigHolder packageConfigHolder;

    @Override
    public AllConfig convert() {
        return new AllConfig()
                .setGlobalConfig(this.globalConfigHolder.convert())
                .setDataSourceConfig(this.dataSourceConfigHolder.convert())
                .setStrategyConfig(this.strategyConfigHolder.convert())
                .setPackageConfig(this.packageConfigHolder.convert());
    }
}
