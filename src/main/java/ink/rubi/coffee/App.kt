package ink.rubi.coffee

import ink.rubi.coffee.config.GUIConfig
import ink.rubi.coffee.controller.MainController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.slf4j.LoggerFactory

/**
 * @author : Rubi
 * @version : 2019-05-17 01:51 上午
 */
class App : Application() {

    private lateinit var window: Stage
    private lateinit var scene: Scene
    private lateinit var layout: Parent
    //    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        window = primaryStage
        val fxmlLoader = FXMLLoader(this.javaClass.getResource("/asset/fxml/App.fxml"))
        layout = fxmlLoader.load()
        fxmlLoader.getController<MainController>().window = window
        window.isResizable = false
        window.title = "Mybatis-plus代码生成器GUI"
        window.icons.add(Image(javaClass.getResourceAsStream("/asset/img/coffee.png")))
        scene = Scene(layout)
        window.scene = scene
        window.show()
    }

    companion object {
        private val log = LoggerFactory.getLogger("[MAIN]")
        @JvmStatic
        fun main(args: Array<String>) {
            log.info("loading conf.properties")
            try {
                GUIConfig.init()
                log.info(System.getProperty("file.encoding"))
                launch(App::class.java, *args)
            } catch (e: Exception) {
                log.error("{0}", e)
//                (((e as RuntimeException).cause as LoadException).cause as IllegalArgumentException).printStackTrace()
            }
        }
    }

}