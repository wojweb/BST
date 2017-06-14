package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Projekt - aplikacja klient-serwer obsługująca drzewa binarne.
 *
 * DrawBox.java
 * Prosta klasa wyświetlająca okienko z wyświetlanymi elementami
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
public class DrawBox {
    public static void display(String elements){
        Stage window = new Stage();
        window.setAlwaysOnTop(true);
        window.setTitle("Draw Box");



        Label treeLabel = new Label();
        treeLabel.setText(elements);

        StackPane stackPane = new StackPane(treeLabel);
        stackPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(stackPane, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
