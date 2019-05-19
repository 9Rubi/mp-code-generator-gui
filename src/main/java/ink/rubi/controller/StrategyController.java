package ink.rubi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import ink.rubi.po.NamingItem;
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
public class StrategyController implements IController<StrategyConfig> {

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
    public StrategyConfig getConfig() {
        return new StrategyConfig()
                .setNameConvert(null)
                .setNaming(NamingStrategy.underline_to_camel).setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(tablePrefix.getText().split(StringPool.COMMA)).setFieldPrefix(fieldPrefix.getText().split(StringPool.COMMA))
                .setSuperMapperClass(superMapperClass.getText()).setSuperEntityColumns(superEntityColumns.getText().split(StringPool.COMMA))
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
    public void flushConfig(StrategyConfig strategyConfig) {
        tablePrefix.setText(String.join(StringPool.COMMA, strategyConfig.getTablePrefix() != null ? strategyConfig.getTablePrefix() : new String[]{}));
        fieldPrefix.setText(String.join(StringPool.COMMA, strategyConfig.getFieldPrefix() != null ? strategyConfig.getFieldPrefix() : new String[]{}));
        superEntityClass.setText(strategyConfig.getSuperEntityClass());
        superEntityColumns.setText(String.join(StringPool.COMMA, strategyConfig.getSuperEntityColumns() != null ? strategyConfig.getSuperEntityColumns() : new String[]{}));
        superMapperClass.setText(strategyConfig.getSuperMapperClass());
        superServiceClass.setText(strategyConfig.getSuperServiceClass());
        superServiceImplClass.setText(strategyConfig.getSuperServiceImplClass());
        superControllerClass.setText(strategyConfig.getSuperControllerClass());
        versionFieldName.setText(strategyConfig.getVersionFieldName());
        logicDeleteFieldName.setText(strategyConfig.getLogicDeleteFieldName());
        entitySerialVersionUID.setSelected(strategyConfig.isEntitySerialVersionUID());
        entityColumnConstant.setSelected(strategyConfig.isEntityColumnConstant());
        entityTableFieldAnnotationEnable.setSelected(strategyConfig.isEntityTableFieldAnnotationEnable());
        entityBuilderModel.setSelected(strategyConfig.isEntityBuilderModel());
        entityLombokModel.setSelected(strategyConfig.isEntityLombokModel());
        entityBooleanColumnRemoveIsPrefix.setSelected(strategyConfig.isEntityBooleanColumnRemoveIsPrefix());
        restControllerStyle.setSelected(strategyConfig.isRestControllerStyle());
        controllerMappingHyphenStyle.setSelected(strategyConfig.isControllerMappingHyphenStyle());
        skipView.setSelected(strategyConfig.isSkipView());
        isCapitalMode.setSelected(strategyConfig.isCapitalMode());
        matchChoice(naming, "namingStrategy", strategyConfig.getNaming(), defaultNaming);
        matchChoice(columnNaming, "namingStrategy", strategyConfig.getColumnNaming(), defaultNaming);
    }


}

