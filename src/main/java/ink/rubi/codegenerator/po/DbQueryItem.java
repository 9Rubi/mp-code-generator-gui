package ink.rubi.codegenerator.po;

import com.baomidou.mybatisplus.generator.config.IDbQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Rubi
 * @version : 2019-05-18 01:53 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbQueryItem implements Item<IDbQuery> {
    private IDbQuery dbQuery;
    private String description;

    @Override
    public IDbQuery getValue() {
        return this.dbQuery;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
