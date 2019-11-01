package ink.rubi.coffee.po

import com.baomidou.mybatisplus.annotation.DbType

/**
 * @author : Rubi
 * @version : 2019-05-18 01:54 上午
 */
data class DbTypeItem(override val value: DbType? = null, override val desc: String? = null) : Item<DbType>
