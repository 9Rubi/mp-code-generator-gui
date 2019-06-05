package ink.rubi.coffee.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rubi
 * @since 2019-06-05 10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldFillItem implements Item<FieldFill> {
    /**
     * 忽略类型
     */
    private FieldFill fieldFill;

    private String description;

    @Override
    public FieldFill getValue() {
        return this.fieldFill;
    }

    @Override
    public String getDesc() {
        return this.description;
    }


}
