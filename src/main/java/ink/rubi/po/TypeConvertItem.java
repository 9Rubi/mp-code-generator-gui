package ink.rubi.po;

import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Rubi
 * @version : 2019-05-18 01:54 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeConvertItem {
    private ITypeConvert typeConvert;
    private String description;
}
