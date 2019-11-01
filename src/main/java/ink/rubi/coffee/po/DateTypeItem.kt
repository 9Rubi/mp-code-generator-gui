package ink.rubi.coffee.po

import com.baomidou.mybatisplus.generator.config.rules.DateType


/**
 * @author : Rubi
 * @version : 2019-05-17 22:00 下午
 */
data class DateTypeItem(override var value: DateType? = null, override var desc: String? = null) : Item<DateType>