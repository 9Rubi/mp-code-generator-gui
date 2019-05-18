package ink.rubi.po;

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
public class NamingItem {
    private NamingStrategy namingStrategy;
    private String description;
}
