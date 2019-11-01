package ink.rubi.coffee.controller

import com.baomidou.mybatisplus.generator.config.ConstVal
import ink.rubi.coffee.constant.Engine
import ink.rubi.coffee.po.holder.TemplateConfigHolder
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import lombok.extern.slf4j.Slf4j
import java.net.URL
import java.util.*

/**
 * @author Rubi
 * @since 2019-06-05 15:42
 */
@Slf4j
class TemplateController : IController<TemplateConfigHolder>, Initializable {
    @FXML
    private lateinit var templatePage: GridPane
    @FXML
    private lateinit var entityLabel: Label
    @FXML
    private lateinit var entityKtLabel: Label
    @FXML
    private lateinit var mapperLabel: Label
    @FXML
    private lateinit var xmlLabel: Label
    @FXML
    private lateinit var serviceLabel: Label
    @FXML
    private lateinit var serviceImplLabel: Label
    @FXML
    private lateinit var controllerLabel: Label
    @FXML
    private lateinit var entityKt: TextField
    @FXML
    private lateinit var entity: TextField
    @FXML
    private lateinit var service: TextField
    @FXML
    private lateinit var serviceImpl: TextField
    @FXML
    private lateinit var mapper: TextField
    @FXML
    private lateinit var xml: TextField
    @FXML
    private lateinit var controller: TextField
    @FXML
    private lateinit var engine: ChoiceBox<String>

    private lateinit var fileChooser: FileChooser

    private var mainController: MainController? = null

    override fun getConfigHolder(): TemplateConfigHolder {
        return TemplateConfigHolder(entity.text, entityKt.text, service.text,
                serviceImpl.text, mapper.text, xml.text, controller.text, engine.value)

    }

    override fun flushConfig(holder: TemplateConfigHolder) {
        controller.text = holder.controller
        engine.value = holder.engine
        entity.text = holder.entity
        entityKt.text = holder.entityKt
        mapper.text = holder.mapper
        service.text = holder.service
        serviceImpl.text = holder.serviceImpl
        xml.text = holder.xml
    }

    override fun inject(mainController: MainController) {
        this.mainController = mainController
    }

    override fun defaultShow() {
        engine.value = defaultEngine
        entityKt.text = ConstVal.TEMPLATE_ENTITY_KT
        entity.text = ConstVal.TEMPLATE_ENTITY_JAVA
        service.text = ConstVal.TEMPLATE_SERVICE
        serviceImpl.text = ConstVal.TEMPLATE_SERVICE_IMPL
        mapper.text = ConstVal.TEMPLATE_MAPPER
        xml.text = ConstVal.TEMPLATE_XML
        controller.text = ConstVal.TEMPLATE_CONTROLLER
    }

    override fun initialize(location: URL, resources: ResourceBundle?) {
        engine.items.addAll(engines)
        defaultShow()
        engine.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
            val oldResult = oldValue == Engine.custom
            val newResult = newValue == Engine.custom
            if (oldResult != newResult) {
                entityKt.isVisible = newResult
                entityKt.text = if (newResult) "" else ConstVal.TEMPLATE_ENTITY_KT
                entity.isVisible = newResult
                entity.text = if (newResult) "" else ConstVal.TEMPLATE_ENTITY_JAVA
                service.isVisible = newResult
                service.text = if (newResult) "" else ConstVal.TEMPLATE_SERVICE
                serviceImpl.isVisible = newResult
                serviceImpl.text = if (newResult) "" else ConstVal.TEMPLATE_SERVICE_IMPL
                mapper.isVisible = newResult
                mapper.text = if (newResult) "" else ConstVal.TEMPLATE_MAPPER
                xml.isVisible = newResult
                xml.text = if (newResult) "" else ConstVal.TEMPLATE_XML
                controller.isVisible = newResult
                controller.text = if (newResult) "" else ConstVal.TEMPLATE_CONTROLLER

                entityLabel.isVisible = newResult
                entityKtLabel.isVisible = newResult
                mapperLabel.isVisible = newResult
                xmlLabel.isVisible = newResult
                serviceLabel.isVisible = newResult
                serviceImplLabel.isVisible = newResult
                controllerLabel.isVisible = newResult
            }
        }
        fileChooser = FileChooser()
        fileChooser.title = "选择模板文件"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("模板文件", "*.*"))
    }


    fun chooseEntity(mouseEvent: MouseEvent) {
        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            entity.text = file.path
        }
    }

    fun chooseEntityKt(mouseEvent: MouseEvent) {
        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            entityKt.text = file.path
        }
    }

    fun chooseMapper(mouseEvent: MouseEvent) {
        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            mapper.text = file.path
        }
    }

    fun chooseXml(mouseEvent: MouseEvent) {
        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            xml.text = file.path
        }
    }

    fun chooseService(mouseEvent: MouseEvent) {

        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            service.text = file.path
        }
    }

    fun chooseServiceImpl(mouseEvent: MouseEvent) {
        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            serviceImpl.text = file.path
        }
    }

    fun chooseController(mouseEvent: MouseEvent) {
        val file = fileChooser.showOpenDialog(mainController!!.window)
        if (file != null) {
            controller.text = file.path
        }
    }

    companion object {
        private val engines = ArrayList(Engine.engines.keys)
        private val defaultEngine = engines[0]
    }
}
