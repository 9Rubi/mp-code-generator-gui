package ink.rubi.coffee.po;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Rubi
 * @version : 2019-05-17 22:10 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdTypeItem implements Item<IdType>{
    private IdType idType;
    private String description;

    @Override
    public IdType getValue() {
        return this.idType;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
