package ink.rubi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

/**
 * @author : Rubi
 * @version : 2019-05-17 01:51 上午
 */

public class App extends Application {
    @Getter
    private static final Logger log = LoggerFactory.getLogger("[MAIN]");

    @Getter
    private static Stage window;
    @Getter
    private Scene scene;
    @Getter
    private Parent layout;
    @Getter
    private Properties prop;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pattern/App.fxml"));
        layout = fxmlLoader.load();

        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Mybatis-plus代码生成器GUI");

        log.info("加载配置文件");
        prop = new Properties();
        prop.load(getClass().getResourceAsStream("/conf.properties"));
        prop.forEach((key, value) -> log.info(key + ":" + value));

        log.info("加载样式");
        scene = new Scene(layout);
        scene.getStylesheets().add("/pattern/App.css");

        window.setScene(scene);
        window.show();
    }

}