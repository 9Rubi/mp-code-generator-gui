package ink.rubi.coffee.controller

import ink.rubi.coffee.po.holder.DataSourceConfigHolder
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import java.net.URL
import java.util.*

/**
 * @author : Rubi
 * @version : 2019-05-17 23:27 下午
 */
class DataSourceController : IController<DataSourceConfigHolder>, Initializable {

    @FXML
    private lateinit var dataSourcePage: GridPane
    @FXML
    private lateinit var url: TextField
    @FXML
    private lateinit var driverName: TextField
    @FXML
    private lateinit var username: TextField
    @FXML
    private lateinit var password: TextField
    @FXML
    private lateinit var schemaName: TextField

    private var mainController: MainController? = null

    override fun initialize(location: URL, resources: ResourceBundle?) {
        defaultShow()
    }

    override fun getConfigHolder(): DataSourceConfigHolder {
        return DataSourceConfigHolder(schemaName.text, url.text, driverName.text, username.text, password.text)
    }

    override fun inject(mainController: MainController) {
        this.mainController = mainController
    }

    override fun defaultShow() {
        url.text = "jdbc:mysql://localhost:3306/table_name?useUnicode=true&useSSL=false&characterEncoding=utf8"
        driverName.text = "com.mysql.jdbc.Driver"
        username.text = "root"
        password.text = ""
        schemaName.text = ""
    }


    override fun flushConfig(holder: DataSourceConfigHolder) {
        url.text = holder.url
        driverName.text = holder.driverName
        username.text = holder.username
        password.text = holder.password
        schemaName.text = holder.schemaName
    }


}
