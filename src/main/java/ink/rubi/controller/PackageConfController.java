package ink.rubi.controller;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import ink.rubi.util.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Rubi
 * @version : 2019-05-18 17:30 下午
 */
public class PackageConfController implements IController<PackageConfig> {
    @FXML
    public TextField parent, moduleName, entity, service, serviceImpl, mapper, xml, controller;
    @FXML
    private GridPane packagePage;
    private MainController mainController;

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public PackageConfig getConfig() {
        return new PackageConfig()
                .setParent(parent.getText()).setModuleName(moduleName.getText())
                .setEntity(entity.getText()).setService(service.getText()).setServiceImpl(serviceImpl.getText())
                .setMapper(mapper.getText()).setXml(xml.getText()).setController(controller.getText()).setPathInfo(null);
    }

    @Override
    public void flushConfig(PackageConfig packageConfig) {
        //important
        parent.setText(Helper.getPackageConfigParent(packageConfig));
        moduleName.setText(packageConfig.getModuleName());
        entity.setText(packageConfig.getEntity());
        service.setText(packageConfig.getService());
        serviceImpl.setText(packageConfig.getServiceImpl());
        mapper.setText(packageConfig.getMapper());
        xml.setText(packageConfig.getXml());
        controller.setText(packageConfig.getController());
    }
}
