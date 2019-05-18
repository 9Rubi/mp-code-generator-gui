package ink.rubi.controller;

import javafx.fxml.Initializable;

/**
 * @author : Rubi
 * @version : 2019-05-17 23:41 下午
 */
public interface IController<T> extends Initializable {
    default T getConfig() {
        return null;
    }
    void init(MainController mainController);
}
