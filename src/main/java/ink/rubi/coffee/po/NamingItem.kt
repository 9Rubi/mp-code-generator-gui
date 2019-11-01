package ink.rubi.coffee.po

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy

/**
 * @author : Rubi
 * @version : 2019-05-18 13:53 下午
 */
data class NamingItem(override var value: NamingStrategy? = null, override var desc: String? = null) : Item<NamingStrategy>