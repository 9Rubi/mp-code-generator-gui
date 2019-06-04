package ink.rubi.codegenerator.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import ink.rubi.codegenerator.po.NamingItem;
import ink.rubi.codegenerator.po.holder.StrategyConfigHolder;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author : Rubi
 * @version : 2019-05-17 22:56 下午
 */
@Getter
public class StrategyController implements IController<StrategyConfigHolder> {

    @FXML
    private TextField tablePrefix, fieldPrefix, superEntityClass, superEntityColumns, superMapperClass,
            superServiceClass, superServiceImplClass, superControllerClass, versionFieldName, logicDeleteFieldName;
    @FXML
    private ChoiceBox<NamingItem> naming, columnNaming;
    @FXML
    private CheckBox entitySerialVersionUID, entityColumnConstant, entityTableFieldAnnotationEnable,
            entityBuilderModel, entityLombokModel, entityBooleanColumnRemoveIsPrefix, restControllerStyle,
            controllerMappingHyphenStyle, skipView, isCapitalMode;
    @FXML
    private GridPane strategyPage;

    private MainController mainController;

    private NamingItem defaultNaming;

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NamingConverter<NamingItem> namingConverter = new NamingConverter<>();

        naming.getItems().addAll(getNamings());
        defaultNaming = naming.getItems().get(1);
        naming.setValue(defaultNaming);
        naming.converterProperty().set(namingConverter);

        columnNaming.getItems().addAll(getNamings());
        columnNaming.setValue(defaultNaming);
        columnNaming.converterProperty().set(namingConverter);

    }


    private List<NamingItem> getNamings() {
        return new ArrayList<NamingItem>() {{
            add(new NamingItem(NamingStrategy.no_change, "不做任何改变，原样输出"));
            add(new NamingItem(NamingStrategy.underline_to_camel, "下划线转驼峰命名"));
        }};
    }

    private class NamingConverter<T extends NamingItem> extends StringConverter<T> {

        @Override
        public String toString(T object) {
            return object.getDescription();
        }

        @Override
        public T fromString(String string) {
            return null;
        }
    }

    @Override
    public StrategyConfigHolder getConfigHolder() {
        return new StrategyConfigHolder()
                .setNameConvert(null)
                .setNaming(naming.getSelectionModel().selectedItemProperty().getValue().getNamingStrategy().name())
                .setColumnNaming(columnNaming.getSelectionModel().selectedItemProperty().getValue().getNamingStrategy().name())
                .setTablePrefix(tablePrefix.getText())
                .setFieldPrefix(fieldPrefix.getText())
                .setSuperEntityColumns(superEntityColumns.getText())
                .setSuperMapperClass(superMapperClass.getText())
                .setSuperControllerClass(superControllerClass.getText()).setSuperServiceClass(superServiceClass.getText())
                .setEntityTableFieldAnnotationEnable(entityTableFieldAnnotationEnable.isSelected())
                .setSuperServiceImplClass(superServiceImplClass.getText())
                .setEntitySerialVersionUID(entitySerialVersionUID.isSelected())
                .setSuperEntityClass(superEntityClass.getText())
                .setEntityColumnConstant(entityColumnConstant.isSelected())
                .setEntityBuilderModel(entityBuilderModel.isSelected())
                .setEntityLombokModel(entityLombokModel.isSelected())
                .setEntityBooleanColumnRemoveIsPrefix(entityBooleanColumnRemoveIsPrefix.isSelected())
                .setRestControllerStyle(restControllerStyle.isSelected())
                .setControllerMappingHyphenStyle(controllerMappingHyphenStyle.isSelected())
                .setVersionFieldName(versionFieldName.getText())
                .setLogicDeleteFieldName(logicDeleteFieldName.getText())
                .setSkipView(skipView.isSelected())
                .setCapitalMode(isCapitalMode.isSelected())
                .setTableFillList(null);

    }

    @Override
    public void flushConfig(StrategyConfigHolder holder) {
        tablePrefix.setText(holder.getTablePrefix());
        fieldPrefix.setText(holder.getFieldPrefix());
        superEntityClass.setText(holder.getSuperEntityClass());
        superEntityColumns.setText(holder.getSuperEntityColumns());
        superMapperClass.setText(holder.getSuperMapperClass());
        superServiceClass.setText(holder.getSuperServiceClass());
        superServiceImplClass.setText(holder.getSuperServiceImplClass());
        superControllerClass.setText(holder.getSuperControllerClass());
        versionFieldName.setText(holder.getVersionFieldName());
        logicDeleteFieldName.setText(holder.getLogicDeleteFieldName());
        entitySerialVersionUID.setSelected(holder.isEntitySerialVersionUID());
        entityColumnConstant.setSelected(holder.isEntityColumnConstant());
        entityTableFieldAnnotationEnable.setSelected(holder.isEntityTableFieldAnnotationEnable());
        entityBuilderModel.setSelected(holder.isEntityBuilderModel());
        entityLombokModel.setSelected(holder.isEntityLombokModel());
        entityBooleanColumnRemoveIsPrefix.setSelected(holder.isEntityBooleanColumnRemoveIsPrefix());
        restControllerStyle.setSelected(holder.isRestControllerStyle());
        controllerMappingHyphenStyle.setSelected(holder.isControllerMappingHyphenStyle());
        skipView.setSelected(holder.isSkipView());
        isCapitalMode.setSelected(holder.isCapitalMode());
        matchChoice(naming, "namingStrategy", holder.getNaming(), defaultNaming);
        matchChoice(columnNaming, "namingStrategy", holder.getColumnNaming(), defaultNaming);
    }


}

