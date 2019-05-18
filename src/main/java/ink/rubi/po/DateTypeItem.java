package ink.rubi.po;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Rubi
 * @version : 2019-05-17 22:00 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateTypeItem {
    private DateType dateType;
    private String description;
}
