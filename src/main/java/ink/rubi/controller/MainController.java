package ink.rubi.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ink.rubi.App;
import ink.rubi.config.GUIConfig;
import ink.rubi.controller.stream.GUIPrintStream;
import ink.rubi.modal.AlertBox;
import ink.rubi.po.ConfigContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Rubi
 * @version : 2019-05-17 12:14 下午
 */
@SuppressWarnings("all")
@Getter
public class MainController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger("[MainController]");
    @FXML
    private Button button;
    @FXML
    private TextArea typeIn, console;
    @FXML
    private GridPane global, dataSource, strategy, packageConf;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button submit;
    @FXML
    private DataSourceController dataSourceController;
    @FXML
    private GlobalController globalController;
    @FXML
    private StrategyController strategyController;
    @FXML
    private PackageConfController packageConfController;

    private FileChooser configFileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console.setEditable(false);

        configFileChooser = new FileChooser();
        configFileChooser.setTitle("选择配置元数据文件");
        configFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("配置文件", "*.json"));

        dataSourceController.init(this);
        globalController.init(this);
        strategyController.init(this);
        packageConfController.init(this);
        if (GUIConfig.showLogInGUIWindow) {
            try {
                redirectSystemOut();
            } catch (FileNotFoundException e) {
                log.error("{}", e);
            }
        }
    }

    private ConfigContainer getAllConfig() {

        return ConfigContainer.builder()
                .globalConfig(globalController.getConfig())
                .dataSourceConfig(dataSourceController.getConfig())
                .packageConfig(packageConfController.getConfig())
                .strategyConfig(strategyController.getConfig())
                .build();


    }

    public void onSubmit(ActionEvent actionEvent) {
        executeGenerator(getAllConfig());
    }

    private void executeGenerator(ConfigContainer configContainer) {
        AutoGenerator generator = new AutoGenerator();

        String[] tableNames = typeIn.getText().split("\\r?\\n");
        for (String tableName : tableNames) {
            //全角空格懒得管了
            tableName = tableName.trim();
        }
        StrategyConfig strategyConfig = configContainer.getStrategyConfig();
        strategyConfig.setInclude(tableNames);
        generator.setGlobalConfig(configContainer.getGlobalConfig())
                .setDataSource(configContainer.getDataSourceConfig())
                /*    .setTemplate(null).setTemplateEngine(null)
                    .setConfig(null).setCfg(null)*/
                .setStrategy(strategyConfig)
                .setPackageInfo(configContainer.getPackageConfig());

        try {
            generator.execute();
        } catch (Exception e) {
            AlertBox.display("异常", "捕捉到一个 " + e.getClass().getSimpleName() + " 异常,请检查配置信息");
        }
    }

    private void redirectSystemOut() throws FileNotFoundException {
        GUIPrintStream guiPrintStream = new GUIPrintStream(System.out, console);
        System.setOut(guiPrintStream);
        System.setErr(guiPrintStream);
    }

    public void showGitHubPage(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/9Rubi/mp-code-generator-gui"));
        } catch (IOException | URISyntaxException e) {
            log.error("{}", e);
        }
    }


    public void readFromFile(ActionEvent actionEvent) {
        File file = configFileChooser.showOpenDialog(App.getWindow());
        if (file != null) {
            ConfigContainer container = null;
            try {
                container = getObjectMapper().readValue(file, ConfigContainer.class);
                flushAllConfig(container);
                log.info("读取成功!");
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
    }

    private void flushAllConfig(ConfigContainer container) throws Exception {
        if (container != null) {
            globalController.flushConfig(container.getGlobalConfig());
            packageConfController.flushConfig(container.getPackageConfig());
            dataSourceController.flushConfig(container.getDataSourceConfig());
            strategyController.flushConfig(container.getStrategyConfig());
        }else {
            throw new Exception("读取错误");
        }
    }

    public void saveToFile(ActionEvent actionEvent) {
        configFileChooser.setInitialFileName("config.json");
        File file = configFileChooser.showSaveDialog(App.getWindow());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(getObjectMapper().writeValueAsString(getAllConfig()));
                writer.flush();
            } catch (IOException e) {
                log.error("{}", e);
                AlertBox.display("异常", "异常为 " + e.getClass().getSimpleName());
            }
            AlertBox.display("提示", "导出成功！");
        }
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configOverride(DbType.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT));
        objectMapper.configOverride(IdType.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT));
        return objectMapper;
    }

    public void onClear(ActionEvent actionEvent) {
        console.setText("");
    }
}
