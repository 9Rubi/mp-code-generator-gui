package ink.rubi.coffee;

import ink.rubi.coffee.config.GUIConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static void main(String[] args) {
        log.info("loading conf.properties");
        try {
            GUIConfig.init();
        } catch (Exception e) {
            log.error("{0}", e);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pattern/App.fxml"));
        layout = fxmlLoader.load();

        window = primaryStage;
        window.setResizable(false);
//        window.setMax
        window.setTitle("Mybatis-plus代码生成器GUI");

        scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

}