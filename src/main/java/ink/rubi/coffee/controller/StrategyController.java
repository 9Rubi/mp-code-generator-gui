package ink.rubi.coffee.controller;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import ink.rubi.coffee.controller.converter.ItemStringConverter;
import ink.rubi.coffee.po.FieldFillItem;
import ink.rubi.coffee.po.NamingItem;
import ink.rubi.coffee.po.TableFillRow;
import ink.rubi.coffee.po.holder.StrategyConfigHolder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author : Rubi
 * @version : 2019-05-17 22:56 下午
 */
@Getter
public class StrategyController implements IController<StrategyConfigHolder> {
   /* @FXML
    private TableColumn fieldNameColumn,fieldFillColumn;*/


    @FXML
    private TableColumn<TableFillRow, String> fieldNameColumn, fieldFillColumn;
    @FXML
    private ChoiceBox<FieldFillItem> fieldFill;
    @FXML
    private TableView<TableFillRow> tableFills;
    @FXML
    private TextField tablePrefix, fieldPrefix, superEntityClass, superEntityColumns, superMapperClass,
            superServiceClass, superServiceImplClass, superControllerClass, versionFieldName, logicDeleteFieldName,
            fieldName;
    @FXML
    private ChoiceBox<NamingItem> naming, columnNaming;
    @FXML
    private CheckBox entitySerialVersionUID, entityColumnConstant, entityTableFieldAnnotationEnable,
            entityBuilderModel, entityLombokModel, entityBooleanColumnRemoveIsPrefix, restControllerStyle,
            controllerMappingHyphenStyle, skipView, isCapitalMode;
    @FXML
    private GridPane strategyPage;

    private MainController mainController;


    private static final List<NamingItem> namings = new ArrayList<NamingItem>() {{
        add(new NamingItem(NamingStrategy.no_change, "不做任何改变，原样输出"));
        add(new NamingItem(NamingStrategy.underline_to_camel, "下划线转驼峰命名"));
    }};
    private static final List<FieldFillItem> fieldFills = new ArrayList<FieldFillItem>() {{
        add(new FieldFillItem(FieldFill.DEFAULT, "NONE"));
        add(new FieldFillItem(FieldFill.INSERT, "插入填充"));
        add(new FieldFillItem(FieldFill.UPDATE, "更新填充"));
        add(new FieldFillItem(FieldFill.INSERT_UPDATE, "插入和更新填充"));
    }};
    private static final FieldFillItem defaultFieldFill = fieldFills.get(1);
    private static final NamingItem defaultNaming = namings.get(1);

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void defaultShow() {
        naming.setValue(defaultNaming);
        columnNaming.setValue(defaultNaming);
        fieldFill.setValue(defaultFieldFill);
        fieldName.setText("create_time");
        superMapperClass.setText("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        superServiceClass.setText("com.baomidou.mybatisplus.extension.service.IService");
        superServiceImplClass.setText("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");

        tablePrefix.clear();
        fieldPrefix.clear();
        superEntityClass.clear();
        superEntityColumns.clear();
        superControllerClass.clear();
        versionFieldName.clear();
        logicDeleteFieldName.clear();


        entitySerialVersionUID.setSelected(true);
        entityTableFieldAnnotationEnable.setSelected(true);
        entityLombokModel.setSelected(true);
        restControllerStyle.setSelected(true);
        controllerMappingHyphenStyle.setSelected(true);
        tableFills.getItems().clear();
        entityColumnConstant.setSelected(false);
        entityBuilderModel.setSelected(false);
        entityBooleanColumnRemoveIsPrefix.setSelected(false);
        skipView.setSelected(false);
        isCapitalMode.setSelected(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ItemStringConverter<NamingItem> namingConverter = new ItemStringConverter<>();

        naming.getItems().addAll(namings);
        naming.converterProperty().set(namingConverter);

        columnNaming.getItems().addAll(namings);
        columnNaming.converterProperty().set(namingConverter);

        fieldFill.getItems().addAll(fieldFills);
        fieldFill.converterProperty().set(new ItemStringConverter<>());

        fieldNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFieldName()));
        fieldFillColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()
                .getFieldFillItem()
                .getValue()
                .name()));
        defaultShow();
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
                .setInclude(mainController.getTypeIn().getText())
                .setTableFillList(convertToMap(tableFills.getItems()));

    }

    private Map<String, String> convertToMap(List<TableFillRow> items) {
        return items.stream().collect(Collectors
                .toMap(TableFillRow::getFieldName, tableFillRow ->
                        tableFillRow.getFieldFillItem().getValue().name()));
    }

    private List<TableFillRow> convertToRow(Map<String, String> items) {
        return StrategyConfigHolder.convertToList(items).stream().map(tableFill -> {
            FieldFillItem value = fieldFills.stream()
                    .filter(item -> item.getValue().equals(tableFill.getFieldFill()))
                    .findAny()
                    .orElse(defaultFieldFill);
            return new TableFillRow(tableFill.getFieldName(), value);
        }).collect(Collectors.toList());
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

        matchChoice(naming, NamingStrategy.class, holder.getNaming(), defaultNaming);
        matchChoice(columnNaming, NamingStrategy.class, holder.getColumnNaming(), defaultNaming);

        tableFills.getItems().addAll(convertToRow(holder.getTableFillList()));

    }

    public void insert(ActionEvent actionEvent) {
        String key = fieldName.getText();
        FieldFillItem value = fieldFill.getSelectionModel().selectedItemProperty().getValue();
        tableFills.getItems().add(new TableFillRow(key, value));
    }

    public void delete(ActionEvent actionEvent) {
        tableFills.getItems().remove(
                tableFills.getSelectionModel().selectedItemProperty().getValue()
        );
    }
}

