package ink.rubi.coffee.controller.converter;

import ink.rubi.coffee.po.Item;
import javafx.util.StringConverter;

/**
 * @author Rubi
 * @since 2019-06-05 10:35
 */
public class ItemStringConverter<T extends Item> extends StringConverter<T> {


    @Override
    public String toString(T item) {
        return item.getDesc();
    }

    @Override
    public T fromString(String string) {
        return null;
    }
}
