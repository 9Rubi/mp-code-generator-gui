package ink.rubi.coffee.controller

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.generator.config.rules.DateType
import ink.rubi.coffee.controller.converter.ItemStringConverter
import ink.rubi.coffee.po.DateTypeItem
import ink.rubi.coffee.po.IdTypeItem
import ink.rubi.coffee.po.holder.GlobalConfigHolder
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.stage.DirectoryChooser
import java.net.URL
import java.util.*

/**
 * @author : Rubi
 * @version : 2019-05-17 22:34 下午
 */
class GlobalController : IController<GlobalConfigHolder>, Initializable {

    @FXML
    private lateinit var outputDir: TextField
    @FXML
    private lateinit var author: TextField
    @FXML
    private lateinit var entityName: TextField
    @FXML
    private lateinit var mapperName: TextField
    @FXML
    private lateinit var xmlName: TextField
    @FXML
    private lateinit var serviceName: TextField
    @FXML
    private lateinit var serviceImplName: TextField
    @FXML
    private lateinit var controllerName: TextField
    @FXML
    private lateinit var open: CheckBox
    @FXML
    private lateinit var fileOverride: CheckBox
    @FXML
    private lateinit var enableCache: CheckBox
    @FXML
    private lateinit var kotlin: CheckBox
    @FXML
    private lateinit var swagger2: CheckBox
    @FXML
    private lateinit var activeRecord: CheckBox
    @FXML
    private lateinit var baseResultMap: CheckBox
    @FXML
    private lateinit var baseColumnList: CheckBox
    @FXML
    private lateinit var dateType: ChoiceBox<DateTypeItem>
    @FXML
    private lateinit var idType: ChoiceBox<IdTypeItem>
    @FXML
    private lateinit var globalPage: GridPane

    private lateinit var mainController: MainController

    private lateinit var chooseGenerateDirectory: DirectoryChooser

    override fun inject(mainController: MainController) {
        this.mainController = mainController
    }

    override fun defaultShow() {
        outputDir.text = "D:\\generate-code"
        author.clear()
        entityName.clear()
        mapperName.clear()
        xmlName.clear()
        serviceName.clear()
        serviceImplName.clear()
        controllerName.clear()
        open.isSelected = true
        fileOverride.isSelected = false
        enableCache.isSelected = false
        kotlin.isSelected = false
        swagger2.isSelected = true
        activeRecord.isSelected = false
        baseResultMap.isSelected = true
        baseColumnList.isSelected = true
        dateType.value = defaultDateType
        idType.value = defaultIdType
    }

    override fun initialize(location: URL, resources: ResourceBundle?) {
        chooseGenerateDirectory = DirectoryChooser()
        chooseGenerateDirectory.title = "选择导出目录"
        dateType.items.addAll(dateTypes)
        dateType.converterProperty().set(ItemStringConverter())
        idType.items.addAll(idTypes)
        idType.converterProperty().set(ItemStringConverter())
        defaultShow()
    }

    fun choose(mouseEvent: MouseEvent) {
        val file = chooseGenerateDirectory.showDialog(mainController.window)
        if (file != null) {
            val path = file.path
            outputDir.text = path
        }
    }


    override fun getConfigHolder(): GlobalConfigHolder {
        val holder = GlobalConfigHolder()
        holder.author = author.text
        holder.outputDir = outputDir.text
        holder.isFileOverride = fileOverride.isSelected
        holder.isEnableCache = enableCache.isSelected
        holder.isActiveRecord = activeRecord.isSelected
        holder.isKotlin = kotlin.isSelected
        holder.isSwagger2 = swagger2.isSelected
        holder.isBaseResultMap = baseResultMap.isSelected
        holder.isBaseColumnList = baseColumnList.isSelected
        holder.entityName = entityName.text
        holder.mapperName = mapperName.text
        holder.xmlName = xmlName.text
        holder.serviceName = serviceName.text
        holder.serviceImplName = serviceImplName.text
        holder.controllerName = controllerName.text
        holder.idType = idType.selectionModel.selectedItemProperty().value.value?.name
        holder.dateType = dateType.selectionModel.selectedItemProperty().value.value!!.name
        holder.isOpen = open.isSelected
        return holder
    }

    override fun flushConfig(holder: GlobalConfigHolder) {
        outputDir.text = holder.outputDir
        author.text = holder.author
        entityName.text = holder.entityName
        mapperName.text = holder.mapperName
        xmlName.text = holder.xmlName
        serviceName.text = holder.serviceName
        serviceImplName.text = holder.serviceImplName
        controllerName.text = holder.controllerName
        open.isSelected = holder.isOpen
        fileOverride.isSelected = holder.isFileOverride
        enableCache.isSelected = holder.isEnableCache
        kotlin.isSelected = holder.isKotlin
        swagger2.isSelected = holder.isSwagger2
        activeRecord.isSelected = holder.isActiveRecord
        baseResultMap.isSelected = holder.isBaseResultMap
        baseColumnList.isSelected = holder.isBaseColumnList
        matchChoice(dateType, DateType::class.java, holder.dateType, defaultDateType)
        matchChoice(idType, IdType::class.java, holder.idType!!, defaultIdType)

    }

    companion object {


        private val dateTypes = object : ArrayList<DateTypeItem>() {
            init {
                add(DateTypeItem(DateType.ONLY_DATE, "只使用 java.util.date 代替"))
                add(DateTypeItem(DateType.SQL_PACK, "使用 java.sql 包下的"))
                add(DateTypeItem(DateType.TIME_PACK, "使用 java.time 包下的,java8 新的时间类型"))
            }
        }

        private val idTypes = object : ArrayList<IdTypeItem>() {
            init {
                add(IdTypeItem(IdType.AUTO, "数据库ID自增"))
                add(IdTypeItem(IdType.NONE, "该类型为未设置主键类型"))
                add(IdTypeItem(IdType.INPUT, "用户输入ID,可以通过自己注册自动填充插件进行填充"))
                add(IdTypeItem(IdType.ID_WORKER, "全局唯一ID (idWorker)"))
                add(IdTypeItem(IdType.UUID, "全局唯一ID (UUID)"))
                add(IdTypeItem(IdType.ID_WORKER_STR, "字符串全局唯一ID (idWorker 的字符串表示)"))
            }
        }

        private val defaultDateType = dateTypes[0]
        private val defaultIdType = idTypes[0]
    }
}
