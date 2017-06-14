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
 * DeleteBox.java
 * Prosta klasa wyświetlająca okienko z informacją kasowaniu elementu
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
public class DeleteBox {
    public static void display(boolean b){
        Stage window = new Stage();
        window.setAlwaysOnTop(true);
        window.setTitle("Delete Box");

        Label messageLabel = new Label();

        if(b)
            messageLabel.setText("Element został wyrzucony z drzewa");
        else
            messageLabel.setText("Element nie zostal wyrzucony z drzewa");


;


        StackPane stackPane = new StackPane(messageLabel);

        Scene scene = new Scene(stackPane, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
