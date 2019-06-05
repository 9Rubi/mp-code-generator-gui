package ink.rubi.coffee.po;

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
public class TypeConvertItem implements Item<ITypeConvert> {
    private ITypeConvert typeConvert;
    private String description;

    @Override
    public ITypeConvert getValue() {
        return this.typeConvert;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
