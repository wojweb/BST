package client;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * Projekt - aplikacja klient-serwer obsługująca drzewa binarne.
 *
 * SearchBox.java
 * Prosta klasa wyświetlająca okienko z informacją o znalezieniu, bądź nie elementu o szukanej wartości w drzewie
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
 class SearchBox {
     static void display(boolean b){
        Stage window = new Stage();
        window.setAlwaysOnTop(true);
        window.setTitle("Search answer");

        Label answer = new Label();
        if(b)
            answer.setText("Szukany obiekt jest w drzewie");
        else
            answer.setText("Szukany obiekt nie jest w drzewie");

        StackPane stackPane = new StackPane(answer);

        Scene scene = new Scene(stackPane, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}
