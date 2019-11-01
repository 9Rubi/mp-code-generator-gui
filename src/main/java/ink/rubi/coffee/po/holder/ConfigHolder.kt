package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.core.toolkit.StringPool

/**
 * @author : Rubi
 * @version : 2019-06-04 23:05 下午
 */
interface ConfigHolder<T> {

    fun convert(): T

    fun format(str: String?): Array<String> {
        return if (str != null && str.isNotEmpty()) {
            str.split(StringPool.COMMA.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        } else arrayOf()
    }
}
