package ink.rubi.coffee.controller;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import ink.rubi.coffee.App;
import ink.rubi.coffee.constant.Engine;
import ink.rubi.coffee.po.holder.TemplateConfigHolder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Rubi
 * @since 2019-06-05 15:42
 */
@Slf4j
public class TemplateController implements IController<TemplateConfigHolder> {
    @FXML
    private Label entityLabel, entityKtLabel, mapperLabel, xmlLabel, serviceLabel, serviceImplLabel, controllerLabel;
    @FXML
    private TextField entityKt, entity, service, serviceImpl, mapper, xml, controller;
    @FXML
    private ChoiceBox<String> engine;

    private FileChooser fileChooser;
    private static final List<String> engines = new ArrayList<>(Engine.engines.keySet());
    private static final String defaultEngine = engines.get(0);

    private MainController mainController;

    @Override
    public TemplateConfigHolder getConfigHolder() {
        return new TemplateConfigHolder()
                .setController(controller.getText())
                .setEngine(engine.getValue())
                .setEntity(entity.getText())
                .setEntityKt(entityKt.getText())
                .setMapper(mapper.getText())
                .setService(service.getText())
                .setServiceImpl(serviceImpl.getText())
                .setXml(xml.getText());
    }

    @Override
    public void flushConfig(TemplateConfigHolder holder) {
        controller.setText(holder.getController());
        engine.setValue(holder.getEngine());
        entity.setText(holder.getEntity());
        entityKt.setText(holder.getEntityKt());
        mapper.setText(holder.getMapper());
        service.setText(holder.getService());
        serviceImpl.setText(holder.getServiceImpl());
        xml.setText(holder.getXml());
    }

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine.getItems().addAll(engines);
        engine.setValue(defaultEngine);
        engine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean oldResult = oldValue.equals(Engine.custom);
            boolean newResult = newValue.equals(Engine.custom);
            if (oldResult != newResult) {
                entityKt.setVisible(newResult);
                entityKt.setText(newResult ? "" : ConstVal.TEMPLATE_ENTITY_KT);
                entity.setVisible(newResult);
                entity.setText(newResult ? "" : ConstVal.TEMPLATE_ENTITY_JAVA);
                service.setVisible(newResult);
                service.setText(newResult ? "" : ConstVal.TEMPLATE_SERVICE);
                serviceImpl.setVisible(newResult);
                serviceImpl.setText(newResult ? "" : ConstVal.TEMPLATE_SERVICE_IMPL);
                mapper.setVisible(newResult);
                mapper.setText(newResult ? "" : ConstVal.TEMPLATE_MAPPER);
                xml.setVisible(newResult);
                xml.setText(newResult ? "" : ConstVal.TEMPLATE_XML);
                controller.setVisible(newResult);
                controller.setText(newResult ? "" : ConstVal.TEMPLATE_CONTROLLER);

                entityLabel.setVisible(newResult);
                entityKtLabel.setVisible(newResult);
                mapperLabel.setVisible(newResult);
                xmlLabel.setVisible(newResult);
                serviceLabel.setVisible(newResult);
                serviceImplLabel.setVisible(newResult);
                controllerLabel.setVisible(newResult);
            }
        });
        fileChooser = new FileChooser();
        fileChooser.setTitle("选择模板文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("模板文件", "*.*"));
    }


    public void chooseEntity(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            entity.setText(file.getPath());
        }
    }

    public void chooseEntityKt(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            entityKt.setText(file.getPath());
        }
    }

    public void chooseMapper(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            mapper.setText(file.getPath());
        }
    }

    public void chooseXml(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            xml.setText(file.getPath());
        }
    }

    public void chooseService(MouseEvent mouseEvent) {

        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            service.setText(file.getPath());
        }
    }

    public void chooseServiceImpl(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            serviceImpl.setText(file.getPath());
        }
    }

    public void chooseController(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            controller.setText(file.getPath());
        }
    }
}
