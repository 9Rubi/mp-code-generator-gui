package ink.rubi.codegenerator.po;

import com.baomidou.mybatisplus.annotation.DbType;
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
public class DbTypeItem implements Item<DbType> {
    private DbType dbType;
    private String description;


    public DbTypeItem(DbType dbType) {
        this.dbType = dbType;
        this.description = dbType.getDesc();
    }

    @Override
    public DbType getValue() {
        return this.dbType;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
