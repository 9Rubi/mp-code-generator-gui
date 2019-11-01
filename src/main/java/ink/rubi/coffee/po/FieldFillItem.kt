package ink.rubi.coffee.po

import com.baomidou.mybatisplus.annotation.FieldFill

/**
 * @author Rubi
 * @since 2019-06-05 10:47
 */
data class FieldFillItem(override var value: FieldFill? = null, override var desc: String? = null) : Item<FieldFill>