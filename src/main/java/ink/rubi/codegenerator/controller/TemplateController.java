package ink.rubi.codegenerator.controller;

import ink.rubi.codegenerator.App;
import ink.rubi.codegenerator.po.holder.TemplateConfigHolder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Rubi
 * @since 2019-06-05 15:42
 */
public class TemplateController implements IController<TemplateConfigHolder> {
    @FXML
    private TextField entityKt, entity, service, serviceImpl, mapper, xml, controller;
    @FXML
    private ChoiceBox<String> engine;

    private FileChooser fileChooser;
    private static final List<String> engines = new ArrayList<String>() {{
        add("velocity");
        add("beetl");
        add("freemarker");
    }};
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

        fileChooser = new FileChooser();
        fileChooser.setTitle("选择模板文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("模板文件", "*.*"));
    }

    public String choose() {
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            return file.getPath();
        }
        return "";
    }


    public void chooseEntity(MouseEvent mouseEvent) {
        entity.setText(choose());
    }

    public void chooseEntityKt(MouseEvent mouseEvent) {
        entityKt.setText(choose());
    }

    public void chooseMapper(MouseEvent mouseEvent) {
        mapper.setText(choose());
    }

    public void chooseXml(MouseEvent mouseEvent) {
        xml.setText(choose());
    }

    public void chooseService(MouseEvent mouseEvent) {
        service.setText(choose());
    }

    public void chooseServiceImpl(MouseEvent mouseEvent) {
        serviceImpl.setText(choose());
    }

    public void chooseController(MouseEvent mouseEvent) {
        controller.setText(choose());
    }
}
