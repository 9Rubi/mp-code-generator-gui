package ink.rubi.coffee.controller;

import ink.rubi.coffee.po.holder.PackageConfigHolder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Rubi
 * @version : 2019-05-18 17:30 下午
 */
public class PackageConfController implements IController<PackageConfigHolder> {
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
    public PackageConfigHolder getConfigHolder() {
        return new PackageConfigHolder()
                .setParent(parent.getText()).setModuleName(moduleName.getText())
                .setEntity(entity.getText()).setService(service.getText()).setServiceImpl(serviceImpl.getText())
                .setMapper(mapper.getText()).setXml(xml.getText()).setController(controller.getText()).setPathInfo(null);
    }

    @Override
    public void flushConfig(PackageConfigHolder holder) {
        //important
        parent.setText(holder.getParent());
//        parent.setText(Helper.getPackageConfigParent(packageConfig));
        moduleName.setText(holder.getModuleName());
        entity.setText(holder.getEntity());
        service.setText(holder.getService());
        serviceImpl.setText(holder.getServiceImpl());
        mapper.setText(holder.getMapper());
        xml.setText(holder.getXml());
        controller.setText(holder.getController());
    }
}
