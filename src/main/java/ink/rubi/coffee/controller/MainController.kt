package ink.rubi.coffee.controller

import com.baomidou.mybatisplus.generator.AutoGenerator
import com.baomidou.mybatisplus.generator.InjectionConfig
import com.baomidou.mybatisplus.generator.config.ConstVal
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import ink.rubi.coffee.config.GUIConfig
import ink.rubi.coffee.controller.stream.GUIPrintStream
import ink.rubi.coffee.modal.AlertBox
import ink.rubi.coffee.po.holder.AllConfigHolder
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TabPane
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import javafx.stage.Window
import lombok.Getter
import org.slf4j.LoggerFactory
import java.awt.Desktop
import java.io.*
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.util.*
import java.util.stream.Stream

/**
 * @author : Rubi
 * @version : 2019-05-17 12:14 下午
 */
val log = LoggerFactory.getLogger("[MainController]")!!

@Getter
class MainController : Initializable {

    @FXML
    private lateinit var button: Button
    @FXML
    lateinit var typeIn: TextArea
    @FXML
    private lateinit var console: TextArea
    @FXML
    private lateinit var global: GridPane
    @FXML
    private lateinit var dataSource: GridPane
    @FXML
    private lateinit var strategy: GridPane
    @FXML
    private lateinit var packageConf: GridPane
    @FXML
    private lateinit var template: GridPane
    @FXML
    private lateinit var tabPane: TabPane
    @FXML
    private lateinit var dataSourceController: DataSourceController
    @FXML
    private lateinit var globalController: GlobalController
    @FXML
    private lateinit var strategyController: StrategyController
    @FXML
    private lateinit var packageConfController: PackageConfController
    @FXML
    private lateinit var templateController: TemplateController
    @FXML
    private lateinit var root: BorderPane

    private lateinit var configFileChooser: FileChooser

    private lateinit var objectMapper: ObjectMapper

    lateinit var window: Window


    private fun getAllConfigHolder(): AllConfigHolder {
        return AllConfigHolder(globalController.getConfigHolder(), dataSourceController.getConfigHolder(),
                strategyController.getConfigHolder(), packageConfController.getConfigHolder(), templateController.getConfigHolder())
    }


    private fun injectMainControllerToOther(vararg controllers: IController<*>) {
        Stream.of(*controllers).forEach { controller -> controller.inject(this) }
    }

    override fun initialize(location: URL, resources: ResourceBundle?) {
        console.isEditable = false
        objectMapper = ObjectMapper()
        configFileChooser = FileChooser()
        configFileChooser.title = "选择配置元数据文件"
        configFileChooser.extensionFilters.add(FileChooser.ExtensionFilter("配置文件", "*.json"))

        if (GUIConfig.isShowLogInGUIWindow) {
            try {
                redirectSystemOut()
            } catch (e: FileNotFoundException) {
                log.error("{0}", e)
            } catch (e: UnsupportedEncodingException) {
                log.error("{0}", e)
            }

        }

        injectMainControllerToOther(dataSourceController, globalController,
                strategyController, packageConfController,
                templateController)


    }

    fun onSubmit() {
        executeGenerator(getAllConfigHolder())
    }


    private fun executeGenerator(configContainer: AllConfigHolder) {
        if (GUIConfig.isLogConfigInfoWhenGenerateCode) {
            try {
                log.warn("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(configContainer))
            } catch (e: JsonProcessingException) {
                log.error("{0}", e)
            }

        }
        val generator = AutoGenerator()
        val (globalConfig, dataSourceConfig, strategyConfig, packageConfig, templateConfig, templateEngine) = configContainer.convert()

        val tableNames = typeIn.text.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in tableNames.indices) {
            tableNames[i] = tableNames[i].trim { it <= ' ' }
        }
        strategyConfig!!.setInclude(*tableNames)
        generator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setTemplate(templateConfig).setTemplateEngine(templateEngine)
                //                .setConfig(null).setCfg(null)
                .setCfg(object : InjectionConfig() {
                    override fun initMap() {

                    }
                })
                .setStrategy(strategyConfig).packageInfo = packageConfig

        try {
            generator.execute()
        } catch (e: Exception) {
            AlertBox.display("异常", "捕捉到一个 " + e.javaClass.simpleName + " 异常,请检查配置信息")
        }

    }

    @Throws(FileNotFoundException::class, UnsupportedEncodingException::class)
    private fun redirectSystemOut() {
        val guiPrintStream = GUIPrintStream(System.out, console)
        System.setOut(guiPrintStream)
        System.setErr(guiPrintStream)
    }

    fun showGitHubPage() {
        try {
            Desktop.getDesktop().browse(URI("https://github.com/9Rubi/mp-code-generator-gui"))
        } catch (e: IOException) {
            log.error("{0}", e)
        } catch (e: URISyntaxException) {
            log.error("{0}", e)
        }

    }


    fun readFromFile() {
        val file = configFileChooser.showOpenDialog(window)
        if (file != null) {
            val container: AllConfigHolder
            try {
                container = objectMapper.readValue(file, AllConfigHolder::class.java)
                flushAllConfig(container)
                log.info("读取成功!")
            } catch (e: Exception) {
                log.error("{0}", e)
            }

        }
    }

    @Throws(Exception::class)
    private fun flushAllConfig(container: AllConfigHolder?) {
        if (container == null) {
            throw Exception("读取错误")
        }
        globalController.flushConfig(container.globalConfigHolder!!)
        packageConfController.flushConfig(container.packageConfigHolder!!)
        dataSourceController.flushConfig(container.dataSourceConfigHolder!!)
        strategyController.flushConfig(container.strategyConfigHolder!!)
        templateController.flushConfig(container.templateConfigHolder!!)
        typeIn.text = container.strategyConfigHolder!!.include
    }

    fun saveToFile() {
        configFileChooser.initialFileName = "config.json"
        val file = configFileChooser.showSaveDialog(window)
        if (file != null) {
            try {
                BufferedWriter(OutputStreamWriter(FileOutputStream(file), ConstVal.UTF8)).use { writer ->
                    writer.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(getAllConfigHolder()))
                    writer.flush()
                    AlertBox.display("提示", "导出成功！")
                }
            } catch (e: IOException) {
                log.error("{0}", e)
                AlertBox.display("异常", "异常为 " + e.javaClass.simpleName)
            }

        }
    }


    fun onClear() {
        console.clear()
    }

    fun onDefault() {
        showDefault(dataSourceController, globalController,
                strategyController, packageConfController,
                templateController)
    }

    private fun showDefault(vararg controllers: IController<*>) {
        Stream.of(*controllers).forEach { it.defaultShow() }
    }

}
