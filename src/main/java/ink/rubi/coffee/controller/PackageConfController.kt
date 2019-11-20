package ink.rubi.coffee.controller

import ink.rubi.coffee.po.holder.PackageConfigHolder
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import java.net.URL
import java.util.*

/**
 * @author : Rubi
 * @version : 2019-05-18 17:30 下午
 */
class PackageConfController : IController<PackageConfigHolder>, Initializable {
    @FXML
    private lateinit var parent: TextField
    @FXML
    private lateinit var moduleName: TextField
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
    private lateinit var packagePage: GridPane
    //    private MainController mainController;

    override fun inject(mainController: MainController) {
        //        this.mainController = mainController;
    }

    override fun defaultShow() {
        parent.text = "com.baomidou"
        moduleName.text = ""
        entity.text = "entity"
        service.text = "service"
        serviceImpl.text = "service.impl"
        mapper.text = "mapper"
        xml.text = "mapper.xml"
        controller.text = "controller"
    }

    override fun initialize(location: URL, resources: ResourceBundle?) {
        defaultShow()
    }

    override fun getConfigHolder(): PackageConfigHolder {
        return PackageConfigHolder(parent.text, moduleName.text, entity.text, service.text, serviceImpl.text, mapper.text, xml.text, controller.text, null)
    }

    override fun flushConfig(holder: PackageConfigHolder) {
        //important
        parent.text = holder.parent
        //        parent.setText(Helper.getPackageConfigParent(packageConfig));
        moduleName.text = holder.moduleName
        entity.text = holder.entity
        service.text = holder.service
        serviceImpl.text = holder.serviceImpl
        mapper.text = holder.mapper
        xml.text = holder.xml
        controller.text = holder.controller
    }
}
