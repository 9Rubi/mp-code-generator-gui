package ink.rubi.codegenerator.controller;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.rubi.codegenerator.App;
import ink.rubi.codegenerator.config.GUIConfig;
import ink.rubi.codegenerator.controller.stream.GUIPrintStream;
import ink.rubi.codegenerator.modal.AlertBox;
import ink.rubi.codegenerator.po.holder.AllConfig;
import ink.rubi.codegenerator.po.holder.AllConfigHolder;
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

    private ObjectMapper objectMapper;

    /* public static ObjectMapper getObjectMapper() {

         objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
         objectMapper.configOverride(DbType.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT));
         objectMapper.configOverride(IdType.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT));
         return objectMapper;
     }*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console.setEditable(false);

        objectMapper = new ObjectMapper();

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

    private AllConfigHolder getAllConfigHolder() {
        return AllConfigHolder.builder()
                .globalConfigHolder(globalController.getConfigHolder())
                .dataSourceConfigHolder(dataSourceController.getConfigHolder())
                .packageConfigHolder(packageConfController.getConfigHolder())
                .strategyConfigHolder(strategyController.getConfigHolder())
                .build();


    }

    public void onSubmit(ActionEvent actionEvent) {
        executeGenerator(getAllConfigHolder());
    }


    private void executeGenerator(AllConfigHolder configContainer) {
        if (GUIConfig.logConfigInfoWhenGenerateCode) {
            try {
                log.warn("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(configContainer));
            } catch (JsonProcessingException e) {
            }

        }
        AutoGenerator generator = new AutoGenerator();
        AllConfig allConfig = configContainer.convert();

        String[] tableNames = typeIn.getText().split("\\r?\\n");
        for (String tableName : tableNames) {
            //全角空格懒得管了
            tableName = tableName.trim();
        }
        StrategyConfig strategyConfig = allConfig.getStrategyConfig();
        strategyConfig.setInclude(tableNames);
        generator.setGlobalConfig(allConfig.getGlobalConfig())
                .setDataSource(allConfig.getDataSourceConfig())
                /*    .setTemplate(null).setTemplateEngine(null)
                    .setConfig(null).setCfg(null)*/
                .setStrategy(strategyConfig)
                .setPackageInfo(allConfig.getPackageConfig());

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
            AllConfigHolder container = null;
            try {
                container = getObjectMapper().readValue(file, AllConfigHolder.class);
                flushAllConfig(container);
                log.info("读取成功!");
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
    }

    private void flushAllConfig(AllConfigHolder container) throws Exception {
        if (container != null) {
            globalController.flushConfig(container.getGlobalConfigHolder());
            packageConfController.flushConfig(container.getPackageConfigHolder());
            dataSourceController.flushConfig(container.getDataSourceConfigHolder());
            strategyController.flushConfig(container.getStrategyConfigHolder());
        } else {
            throw new Exception("读取错误");
        }
    }

    public void saveToFile(ActionEvent actionEvent) {
        configFileChooser.setInitialFileName("config.json");
        File file = configFileChooser.showSaveDialog(App.getWindow());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(getAllConfigHolder()));
                writer.flush();
            } catch (IOException e) {
                log.error("{}", e);
                AlertBox.display("异常", "异常为 " + e.getClass().getSimpleName());
            }
            AlertBox.display("提示", "导出成功！");
        }
    }


    public void onClear(ActionEvent actionEvent) {
        console.setText("");
    }
}
