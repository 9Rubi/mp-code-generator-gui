package ink.rubi.coffee.controller

import ink.rubi.coffee.po.Item
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import java.util.*

/**
 * @author : Rubi
 * @version : 2019-05-17 23:41 下午
 */
interface IController<T> {
    fun getConfigHolder(): T

    fun flushConfig(holder: T) {}

    /**
     * for the communication between mainController and otherControllers.
     *
     * @param mainController
     */
    fun inject(mainController: MainController)

    fun defaultShow()

    fun <I : Item<*>, T : Enum<T>> matchChoice(box: ChoiceBox<I>, enumClazz: Class<T>, strValue: String, defaultValue: I) {
        box.selectionModel.select(
                box.items
                        .stream()
//                        .filter { item -> item.value == T.valueOf<T>(enumClazz, strValue) }
                        .filter { item -> item.value == EnumSet.allOf(enumClazz).find { t -> t.name == strValue } }
                        .findAny()
                        .orElse(defaultValue))
    }
}
