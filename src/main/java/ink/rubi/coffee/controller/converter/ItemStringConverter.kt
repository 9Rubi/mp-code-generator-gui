package ink.rubi.coffee.controller.converter

import ink.rubi.coffee.po.Item
import javafx.util.StringConverter

/**
 * @author Rubi
 * @since 2019-06-05 10:35
 */
class ItemStringConverter<T : Item<*>> : StringConverter<T>() {


    override fun toString(item: T): String? {
        return item.desc
    }

    override fun fromString(string: String): T? {
        return null
    }
}
