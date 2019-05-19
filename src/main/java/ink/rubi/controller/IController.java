package ink.rubi.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.lang.reflect.Field;

/**
 * @author : Rubi
 * @version : 2019-05-17 23:41 下午
 */
public interface IController<T> extends Initializable {
    default T getConfig() {
        return null;
    }

    default void flushConfig(T t) {
    }

    void init(MainController mainController);

    @SuppressWarnings("all")
    default <C, E> void matchChoice(ChoiceBox<C> choiceBox, String fieldName, E config, C defaultConfig) {

        choiceBox.getSelectionModel().select(choiceBox.getItems().stream()
                .filter(item -> {
                    Object obj = null;
                    Field field = null;
                    try {
                        //控件内 每个类的naming域
                        field = item.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        //得到naming的值
                        obj = field.get(item);
                    } catch (Exception e) {

                    }
                    return obj.getClass().isEnum() ? obj.equals(config) : obj.getClass().isInstance(config);
                })
                .findAny()
                .orElse(defaultConfig));
    }
}
