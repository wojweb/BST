package client;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Projekt - aplikacja klient-serwer obsługująca drzewa binarne.
 *
 * InsertBox.java
 * Prosta klasa wyświetlająca okienko z informacją o wstawieniu elementu do drzewa
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
public class InsertBox {
    public static void display(boolean b){
        Stage window = new Stage();
        window.setAlwaysOnTop(true);
        window.setTitle("Insert Box");

        Label messageLabel = new Label();

        if(b)
            messageLabel.setText("Element został włożony do drzewa");
        else
            messageLabel.setText("Element nie zostal wlozony do drzea");



        StackPane stackPane = new StackPane(messageLabel);

        Scene scene = new Scene(stackPane, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
