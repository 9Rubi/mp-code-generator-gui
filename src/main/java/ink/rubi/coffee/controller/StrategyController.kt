package ink.rubi.coffee.controller

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
import ink.rubi.coffee.controller.converter.ItemStringConverter
import ink.rubi.coffee.po.FieldFillItem
import ink.rubi.coffee.po.NamingItem
import ink.rubi.coffee.po.TableFillRow
import ink.rubi.coffee.po.holder.StrategyConfigHolder
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import lombok.Getter
import java.net.URL
import java.util.*
import java.util.stream.Collectors

/**
 * @author : Rubi
 * @version : 2019-05-17 22:56 下午
 */
@Getter
class StrategyController : IController<StrategyConfigHolder>, Initializable {
    @FXML
    private lateinit var fieldNameColumn: TableColumn<TableFillRow, String>
    @FXML
    private lateinit var fieldFillColumn: TableColumn<TableFillRow, String>
    @FXML
    private lateinit var fieldFill: ChoiceBox<FieldFillItem>
    @FXML
    private lateinit var tableFills: TableView<TableFillRow>
    @FXML
    private lateinit var tablePrefix: TextField
    @FXML
    private lateinit var fieldPrefix: TextField
    @FXML
    private lateinit var superEntityClass: TextField
    @FXML
    private lateinit var superEntityColumns: TextField
    @FXML
    private lateinit var superMapperClass: TextField
    @FXML
    private lateinit var superServiceClass: TextField
    @FXML
    private lateinit var superServiceImplClass: TextField
    @FXML
    private lateinit var superControllerClass: TextField
    @FXML
    private lateinit var versionFieldName: TextField
    @FXML
    private lateinit var logicDeleteFieldName: TextField
    @FXML
    private lateinit var fieldName: TextField
    @FXML
    private lateinit var naming: ChoiceBox<NamingItem>
    @FXML
    private lateinit var columnNaming: ChoiceBox<NamingItem>
    @FXML
    private lateinit var entitySerialVersionUID: CheckBox
    @FXML
    private lateinit var entityColumnConstant: CheckBox
    @FXML
    private lateinit var entityTableFieldAnnotationEnable: CheckBox
    @FXML
    private lateinit var entityBuilderModel: CheckBox
    @FXML
    private lateinit var entityLombokModel: CheckBox
    @FXML
    private lateinit var entityBooleanColumnRemoveIsPrefix: CheckBox
    @FXML
    private lateinit var restControllerStyle: CheckBox
    @FXML
    private lateinit var controllerMappingHyphenStyle: CheckBox
    @FXML
    private lateinit var skipView: CheckBox
    @FXML
    private lateinit var isCapitalMode: CheckBox
    @FXML
    private lateinit var strategyPage: GridPane

    private lateinit var mainController: MainController

    override fun inject(mainController: MainController) {
        this.mainController = mainController
    }

    override fun defaultShow() {
        naming.value = defaultNaming
        columnNaming.value = defaultNaming
        fieldFill.value = defaultFieldFill
        fieldName.text = "create_time"
        superMapperClass.text = "com.baomidou.mybatisplus.core.mapper.BaseMapper"
        superServiceClass.text = "com.baomidou.mybatisplus.extension.service.IService"
        superServiceImplClass.text = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl"

        tablePrefix.text = ""
        fieldPrefix.text = ""
        superEntityClass.text = ""
        superEntityColumns.text = ""
        superControllerClass.text = ""
        versionFieldName.text = ""
        logicDeleteFieldName.text = ""


        entitySerialVersionUID.isSelected = true
        entityTableFieldAnnotationEnable.isSelected = true
        entityLombokModel.isSelected = true
        restControllerStyle.isSelected = true
        controllerMappingHyphenStyle.isSelected = true
        tableFills.items.clear()
        entityColumnConstant.isSelected = false
        entityBuilderModel.isSelected = false
        entityBooleanColumnRemoveIsPrefix.isSelected = false
        skipView.isSelected = false
        isCapitalMode.isSelected = false
    }

    override fun initialize(location: URL, resources: ResourceBundle?) {
        val namingConverter = ItemStringConverter<NamingItem>()

        naming.items.addAll(namings)
        naming.converterProperty().set(namingConverter)

        columnNaming.items.addAll(namings)
        columnNaming.converterProperty().set(namingConverter)

        fieldFill.items.addAll(fieldFills)
        fieldFill.converterProperty().set(ItemStringConverter())

        fieldNameColumn.setCellValueFactory { param -> SimpleStringProperty(param.value.fieldName) }
        fieldFillColumn.setCellValueFactory { param ->
            SimpleStringProperty(param.value
                    .fieldFillItem!!
                    .value!!
                    .name)
        }
        defaultShow()
    }


    override fun getConfigHolder(): StrategyConfigHolder {
        val holder = StrategyConfigHolder()
        holder.nameConvert = null
        holder.naming = naming.selectionModel.selectedItemProperty().value.value!!.name
        holder.columnNaming = columnNaming.selectionModel.selectedItemProperty().value.value!!.name
        holder.tablePrefix = tablePrefix.text
        holder.fieldPrefix = fieldPrefix.text
        holder.superEntityColumns = superEntityColumns.text
        holder.superMapperClass = superMapperClass.text
        holder.superControllerClass = superControllerClass.text
        holder.superServiceClass = superServiceClass.text
        holder.isEntityTableFieldAnnotationEnable = entityTableFieldAnnotationEnable.isSelected
        holder.superServiceImplClass = superServiceImplClass.text
        holder.isEntitySerialVersionUID = entitySerialVersionUID.isSelected
        holder.superEntityClass = superEntityClass.text
        holder.isEntityColumnConstant = entityColumnConstant.isSelected
        holder.isEntityBuilderModel = entityBuilderModel.isSelected
        holder.isEntityLombokModel = entityLombokModel.isSelected
        holder.isEntityBooleanColumnRemoveIsPrefix = entityBooleanColumnRemoveIsPrefix.isSelected
        holder.isRestControllerStyle = restControllerStyle.isSelected
        holder.isControllerMappingHyphenStyle = controllerMappingHyphenStyle.isSelected
        holder.versionFieldName = versionFieldName.text
        holder.logicDeleteFieldName = logicDeleteFieldName.text
        holder.isSkipView = skipView.isSelected
        holder.isCapitalMode = isCapitalMode.isSelected
        holder.include = mainController.typeIn.text
        holder.tableFillList = convertToMap(tableFills.items)
        return holder
    }


    override fun flushConfig(holder: StrategyConfigHolder) {
        tablePrefix.text = holder.tablePrefix
        fieldPrefix.text = holder.fieldPrefix
        superEntityClass.text = holder.superEntityClass
        superEntityColumns.text = holder.superEntityColumns
        superMapperClass.text = holder.superMapperClass
        superServiceClass.text = holder.superServiceClass
        superServiceImplClass.text = holder.superServiceImplClass
        superControllerClass.text = holder.superControllerClass
        versionFieldName.text = holder.versionFieldName
        logicDeleteFieldName.text = holder.logicDeleteFieldName
        entitySerialVersionUID.isSelected = holder.isEntitySerialVersionUID
        entityColumnConstant.isSelected = holder.isEntityColumnConstant
        entityTableFieldAnnotationEnable.isSelected = holder.isEntityTableFieldAnnotationEnable
        entityBuilderModel.isSelected = holder.isEntityBuilderModel
        entityLombokModel.isSelected = holder.isEntityLombokModel
        entityBooleanColumnRemoveIsPrefix.isSelected = holder.isEntityBooleanColumnRemoveIsPrefix
        restControllerStyle.isSelected = holder.isRestControllerStyle
        controllerMappingHyphenStyle.isSelected = holder.isControllerMappingHyphenStyle
        skipView.isSelected = holder.isSkipView
        isCapitalMode.isSelected = holder.isCapitalMode

        matchChoice(naming, NamingStrategy::class.java, holder.naming, defaultNaming)
        matchChoice(columnNaming, NamingStrategy::class.java, holder.columnNaming!!, defaultNaming)

        tableFills.items.addAll(convertToRow(holder.tableFillList)!!)

    }

    fun insert() {
        val key = fieldName.text
        val value = fieldFill.selectionModel.selectedItemProperty().value
        tableFills.items.add(TableFillRow(key, value))
    }

    fun delete() {
        tableFills.items.remove(
                tableFills.selectionModel.selectedItemProperty().value
        )
    }


    companion object {


        private val namings = object : ArrayList<NamingItem>() {
            init {
                add(NamingItem(NamingStrategy.no_change, "不做任何改变，原样输出"))
                add(NamingItem(NamingStrategy.underline_to_camel, "下划线转驼峰命名"))
            }
        }
        private val fieldFills = object : ArrayList<FieldFillItem>() {
            init {
                add(FieldFillItem(FieldFill.DEFAULT, "NONE"))
                add(FieldFillItem(FieldFill.INSERT, "插入填充"))
                add(FieldFillItem(FieldFill.UPDATE, "更新填充"))
                add(FieldFillItem(FieldFill.INSERT_UPDATE, "插入和更新填充"))
            }
        }
        private val defaultFieldFill = fieldFills[1]
        private val defaultNaming = namings[1]
    }

    private fun convertToMap(items: List<TableFillRow>): Map<String, String>? {
        return items.stream().collect(Collectors.toMap({ t: TableFillRow -> t.fieldName!! }) { tableFillRow -> tableFillRow.fieldFillItem!!.value!!.name })
    }

    private fun convertToRow(items: Map<String, String>?): MutableList<TableFillRow>? {
        return StrategyConfigHolder.convertToList(items)!!.stream().map { tableFill ->
            var value = fieldFills
                    .stream().filter({ item -> item.value!!.equals(tableFill.fieldFill) }).findAny().orElse(defaultFieldFill)
            return@map TableFillRow(tableFill.fieldName, value)
        }.collect(Collectors.toList())
    }
}
/*
    private List<TableFillRow> convertToRow(Map<String, String> items) {
        return StrategyConfigHolder.Companion.convertToList(items).stream().map(tableFill -> {
            FieldFillItem value = fieldFills.stream()
                    .filter(item -> item.getValue().equals(tableFill.getFieldFill()))
                    .findAny()
                    .orElse(defaultFieldFill);
            return new TableFillRow(tableFill.getFieldName(), value);
        }).collect(Collectors.toList());
    }
 */
