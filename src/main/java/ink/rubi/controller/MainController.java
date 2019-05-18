package ink.rubi.controller;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import ink.rubi.modal.AlertBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console.setEditable(false);
        dataSourceController.init(this);
        globalController.init(this);
        strategyController.init(this);
        packageConfController.init(this);
//        pushSystemOutToTextArea();
    }

    public void buttonOnClick(ActionEvent actionEvent) {
        executeGenerator(globalController.getConfig(), strategyController.getConfig(),
                dataSourceController.getConfig(), packageConfController.getConfig());
    }

    private void executeGenerator(GlobalConfig globalConfig, StrategyConfig strategyConfig,
                                  DataSourceConfig dataSourceConfig, PackageConfig packageConfig) {
        AutoGenerator generator = new AutoGenerator();


        String[] tableNames = typeIn.getText().split("\\r?\\n");
        for (String tableName : tableNames) {
            //全角空格懒得管了
            tableName = tableName.trim();
        }
        strategyConfig.setInclude(tableNames);

        generator.setGlobalConfig(globalConfig).setDataSource(dataSourceConfig)
                /*    .setTemplate(null).setTemplateEngine(null)
                    .setConfig(null).setCfg(null)*/
                .setStrategy(strategyConfig).setPackageInfo(packageConfig);

        try {
            generator.execute();
        } catch (Exception e) {
            AlertBox.display("异常", "捕捉到一个 " + e.getClass().getSimpleName() + " 异常,请检查配置信息");
        }
        log.info("{}", globalConfig);
        log.info("{}", strategyConfig);
        log.info("{}", dataSourceConfig);
    }


    private void pushSystemOutToTextArea() {
        WindowConsoleOutputStream out = new WindowConsoleOutputStream();
        System.setOut(new PrintStream(out, true));
        System.setErr(System.out);
    }


    class WindowConsoleOutputStream extends OutputStream {
        @Override
        public void write(int b) {
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            if (b == null) {
                throw new NullPointerException();
            } else if ((off < 0) || (off > b.length) || (len < 0) ||
                    ((off + len) > b.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }
            Platform.runLater(() -> console.appendText(new String(b, StandardCharsets.UTF_8)));
        }
    }


}
