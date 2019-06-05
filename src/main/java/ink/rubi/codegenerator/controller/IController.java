package ink.rubi.codegenerator.controller;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import ink.rubi.codegenerator.po.Item;
import ink.rubi.codegenerator.po.NamingItem;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.lang.reflect.Field;

/**
 * @author : Rubi
 * @version : 2019-05-17 23:41 下午
 */
public interface IController<T> extends Initializable {
    default T getConfigHolder() {
        return null;
    }

    default void flushConfig(T t) {
    }

    /**
     * for the communication between mainController and otherControllers.
     * @param mainController
     */
    void init(MainController mainController);


    default <I extends Item, T extends Enum<T>> void matchChoice(ChoiceBox<I> box, Class<T> enumClazz, String strValue, I defaultValue) {
        box.getSelectionModel().select(
                box.getItems()
                        .stream()
                        .filter(item -> item.getValue().equals(T.valueOf(enumClazz, strValue)))
                        .findAny()
                        .orElse(defaultValue));
    }
}
