package ink.rubi.coffee.modal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author : Rubi
 * @version : 2019-05-18 14:36 下午
 */
public class AlertBox {
    public static void display(String title, String message) {
        Stage window = getNewWindow(title, message);
        window.show();
    }

    public static void displayAndWait(String title, String message) {
        Stage window = getNewWindow(title, message);
        window.showAndWait();
    }

    private static Stage getNewWindow(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(335);
        window.setMinHeight(144);

        Label label = new Label(message);

        Button closeButton = new Button("知道了");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(10);
        label.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        return window;
    }


}
