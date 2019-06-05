package ink.rubi.codegenerator.po;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Rubi
 * @version : 2019-05-18 13:53 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamingItem implements Item<NamingStrategy> {
    private NamingStrategy namingStrategy;
    private String description;

    @Override
    public NamingStrategy getValue() {
        return this.namingStrategy;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
