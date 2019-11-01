package ink.rubi.coffee.po

import com.baomidou.mybatisplus.annotation.IdType

/**
 * @author : Rubi
 * @version : 2019-05-17 22:10 下午
 */
data class IdTypeItem(override var value: IdType? = null, override var desc: String? = null) : Item<IdType>