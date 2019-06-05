package ink.rubi.coffee.po.holder;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * @author : Rubi
 * @version : 2019-06-04 23:05 下午
 */
public interface ConfigHolder<T> {

    T convert();

    default String[] format(String str) {
        if (str != null && !str.isEmpty()) {
            return str.split(StringPool.COMMA);
        }
        return null;
    }
}
