package ink.rubi.coffee.controller;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.rubi.coffee.config.GUIConfig;
import ink.rubi.coffee.controller.stream.GUIPrintStream;
import ink.rubi.coffee.modal.AlertBox;
import ink.rubi.coffee.po.holder.AllConfig;
import ink.rubi.coffee.po.holder.AllConfigHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * @author : Rubi
 * @version : 2019-05-17 12:14 下午
 */
@SuppressWarnings("unused")
@Getter
public class MainController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger("[MainController]");

    @FXML
    private Button button;
    @FXML
    private TextArea typeIn, console;
    @FXML
    private GridPane global, dataSource, strategy, packageConf, template;
    @FXML
    private TabPane tabPane;
    @FXML
    private DataSourceController dataSourceController;
    @FXML
    private GlobalController globalController;
    @FXML
    private StrategyController strategyController;
    @FXML
    private PackageConfController packageConfController;
    @FXML
    private TemplateController templateController;
    @FXML
    private BorderPane root;

    private FileChooser configFileChooser;

    private ObjectMapper objectMapper;


    private void init(IController... controllers) {
        Stream.of(controllers).forEach(controller -> controller.init(this));
    }

    public Window getWindow() {
        return root.getScene().getWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console.setEditable(false);

        objectMapper = new ObjectMapper();

        configFileChooser = new FileChooser();
        configFileChooser.setTitle("选择配置元数据文件");
        configFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("配置文件", "*.json"));

        if (GUIConfig.isShowLogInGUIWindow()) {
            try {
                redirectSystemOut();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                log.error("{0}", e);
            }
        }

        init(dataSourceController, globalController,
                strategyController, packageConfController,
                templateController);


    }

    private AllConfigHolder getAllConfigHolder() {
        return AllConfigHolder.builder()
                .globalConfigHolder(globalController.getConfigHolder())
                .dataSourceConfigHolder(dataSourceController.getConfigHolder())
                .packageConfigHolder(packageConfController.getConfigHolder())
                .strategyConfigHolder(strategyController.getConfigHolder())
                .templateConfigHolder(templateController.getConfigHolder())
                .build();


    }

    public void onSubmit(ActionEvent actionEvent) {
        executeGenerator(getAllConfigHolder());
    }


    private void executeGenerator(AllConfigHolder configContainer) {
        if (GUIConfig.isLogConfigInfoWhenGenerateCode()) {
            try {
                log.warn("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(configContainer));
            } catch (JsonProcessingException e) {
                log.error("{0}", e);
            }

        }
        AutoGenerator generator = new AutoGenerator();
        AllConfig allConfig = configContainer.convert();

        String[] tableNames = typeIn.getText().split("\\r?\\n");
        for (int i = 0; i < tableNames.length; i++) {
            tableNames[i] = tableNames[i].trim();
        }
        StrategyConfig strategyConfig = allConfig.getStrategyConfig();
        strategyConfig.setInclude(tableNames);
        generator.setGlobalConfig(allConfig.getGlobalConfig())
                .setDataSource(allConfig.getDataSourceConfig())
                .setTemplate(allConfig.getTemplateConfig()).setTemplateEngine(allConfig.getTemplateEngine())
//                .setConfig(null).setCfg(null)
                .setStrategy(strategyConfig)
                .setPackageInfo(allConfig.getPackageConfig());

        try {
            generator.execute();
        } catch (Exception e) {
            AlertBox.display("异常", "捕捉到一个 " + e.getClass().getSimpleName() + " 异常,请检查配置信息");
        }
    }

    private void redirectSystemOut() throws FileNotFoundException, UnsupportedEncodingException {
        GUIPrintStream guiPrintStream = new GUIPrintStream(System.out, console);
        System.setOut(guiPrintStream);
        System.setErr(guiPrintStream);
    }

    public void showGitHubPage(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/9Rubi/mp-code-generator-gui"));
        } catch (IOException | URISyntaxException e) {
            log.error("{0}", e);
        }
    }


    public void readFromFile(ActionEvent actionEvent) {
        File file = configFileChooser.showOpenDialog(getWindow());
        if (file != null) {
            AllConfigHolder container;
            try {
                container = getObjectMapper().readValue(file, AllConfigHolder.class);
                flushAllConfig(container);
                log.info("读取成功!");
            } catch (Exception e) {
                log.error("{0}", e);
            }
        }
    }

    private void flushAllConfig(AllConfigHolder container) throws Exception {
        if (container == null) {
            throw new Exception("读取错误");
        }
        globalController.flushConfig(container.getGlobalConfigHolder());
        packageConfController.flushConfig(container.getPackageConfigHolder());
        dataSourceController.flushConfig(container.getDataSourceConfigHolder());
        strategyController.flushConfig(container.getStrategyConfigHolder());
        templateController.flushConfig(container.getTemplateConfigHolder());
        typeIn.setText(container.getStrategyConfigHolder().getInclude());
    }

    public void saveToFile(ActionEvent actionEvent) {
        configFileChooser.setInitialFileName("config.json");
        File file = configFileChooser.showSaveDialog(getWindow());
        if (file != null) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ConstVal.UTF8))) {
                writer.write(getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(getAllConfigHolder()));
                writer.flush();
                AlertBox.display("提示", "导出成功！");
            } catch (IOException e) {
                log.error("{0}", e);
                AlertBox.display("异常", "异常为 " + e.getClass().getSimpleName());
            }

        }
    }


    public void onClear(ActionEvent actionEvent) {
        console.clear();
    }

    public void onDefault(ActionEvent actionEvent) {
        showDefault(dataSourceController, globalController,
                strategyController, packageConfController,
                templateController);
    }

    private void showDefault(IController... controllers) {
        Stream.of(controllers).forEach(IController::defaultShow);
    }
}
