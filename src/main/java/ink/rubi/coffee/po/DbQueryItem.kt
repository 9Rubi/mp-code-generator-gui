package ink.rubi.coffee.po

import com.baomidou.mybatisplus.generator.config.IDbQuery

/**
 * @author : Rubi
 * @version : 2019-05-18 01:53 上午
 */
data class DbQueryItem(override var value: IDbQuery? = null, override var desc: String? = null) : Item<IDbQuery>
