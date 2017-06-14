package client;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *  Projekt - aplikacja klient-serwer obsługująca drzewa binarne
 *
 *  ClientBST.java
 *  Klasa łączy się z serwerem, a następnie tworzy proste GUI klienta w którym można tworzyć i usuwać drzewa
 *  wybranego przez siebie typu, a następnie dodawać/wyszukiwać/usuwać/wyświetlać ich obiekty.
 *  Klasa wyświetla do serwera kody z informacjami co powinien zrobić
 *  oraz z wartościami do poleceń odzielone kropkami.
 *
 *  Komunikaty wyświetlane przez terminal są używane tylko przeze mnie do testowania aplikacji.
 *
 *  @author Szymon Wojtaszek 236592
 *  @version Do oddania
 */

public class ClientBST extends Application {

    //Network
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    //client.GUI
    private TableView<TreeInfo> table;
    private TextField keyTextField;
    private TextField nameInput;
    private ComboBox<String> typeComboBox;

    private int numberOfTrees = 0;


    public static void main(String[] args) {
        plugIn();
        launch(args);

        plugOut();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        window.setTitle("BST - Client");



        Scene scene = new Scene(createContent());
        window.setScene(scene);
        window.show();
    }

    /**
     * Łączy klienta z serwerem, który w tym przypadku jest uruchomiony na tym samym komputerze.
     */
    private static void plugIn(){
        try{
            clientSocket = new Socket("localhost", 9978);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Connected");



        }catch (UnknownHostException e){
            System.out.println("Nie znaleziono hosta");
            System.exit(0);
        }catch (IOException e){
            System.out.println("IO error");
            System.exit(0);
        }catch (Exception e){
            System.out.println("Reszta");
        }

    }

    /**
     * Bezpiecznie zamyka połączenie
     */
    private static void plugOut(){
        try{
            clientSocket.close();
            out.close();
            in.close();
            System.out.println("Disconnected");
        }catch (IOException e){
            System.out.println("clientSocket niepoprawnie zamkniety");
        }
        try{
            in.close();
            out.close();
        }catch (IOException e){
            System.out.println("Wejscie/wyjście niepoprawnie zamkniete");
        }

    }

    /**
     * Tworzy lauout klienta, składający się z dwóch paneli z kontrolkami oraz listy drzew.
     * @return
     */
    private Parent createContent(){
        //Name column
        TableColumn<TreeInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("treeName"));

        //Type column
        TableColumn<TreeInfo, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));

        //Number column
        TableColumn<TreeInfo, Integer> numberColumn = new TableColumn<>("Number");
        numberColumn.setMinWidth(100);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));




        table = new TableView<>();
        table.getColumns().addAll(nameColumn, typeColumn, numberColumn);

        //Name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);


        //type input
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll(
                "Integer",
                "Double",
                "String"
        );

        //Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtomClicked());

        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(10,10,10,10));
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(nameInput, typeComboBox, addButton, deleteButton);

        keyTextField = new TextField();
        keyTextField.setPromptText("Key");
        Button searchKeyButton = new Button("Search");
        searchKeyButton.setOnAction(e -> {
            if(table.getSelectionModel().getSelectedItem() != null)
                searchKeyButtonClicked();
        });
        Button insertKeyButton = new Button("Insert");
        insertKeyButton.setOnAction(e -> {
            if(table.getSelectionModel().getSelectedItem() != null)
                insertKeyButtonClicked();
        });

        Button deleteKeyButton = new Button("Delete");
        deleteKeyButton.setOnAction(e -> {
            if(table.getSelectionModel().getSelectedItem() != null)
                deleteKeyButtonClicked();
        });

        Button drawButton = new Button("Draw");
        drawButton.setOnAction(e -> {
            if(table.getSelectionModel().getSelectedItem() != null)
                drawButtonClicked();
        });



        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10,10,10,10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(keyTextField, searchKeyButton, insertKeyButton, deleteKeyButton, drawButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox2, table, hBox1);

        return vBox;

    }

    /**
     * Wysyła do serwera kod "4.numer_drzewa.wartość" i oczekuje na informacje zwrotną, czy podana wartość
     * znajduje się w tym zaznaczonym na panelu drzewie. Następnie wyświetla efekt w nowym okienku.
     */
    public void searchKeyButtonClicked(){
        int treeNumber = table.getSelectionModel().getSelectedItem().getNumber();
        String value = keyTextField.getText();

        out.println("4." + treeNumber + "." + value);

        try{
            String input = in.readLine();
            if(input.equals("4.true"))
                SearchBox.display(true);
            else
                SearchBox.display(false);
            System.out.println(input);
        }catch (IOException e){
            System.out.println("Zle pobrano dane przy wstawianiu do drzewa");
        }


        //SearchBox.display(b);
    }

    /**
     * Wysyła do serwera kod "2.numer_drzewa.wartość" i oczekuje na informacje zwrotną,
     * czy do zaznaczonego drzewa udało się włożyć wartość. Przy prawidłowym korzystaniu z programu powinno
     * zawsze zwracać "2.true". Na koniec wyświetla wynik w nowym okienku.
     */
    public void insertKeyButtonClicked(){
        int treeNumber = table.getSelectionModel().getSelectedItem().getNumber();
        String value = keyTextField.getText();

        out.println("2." + treeNumber + "." + value);

        try{
            String input = in.readLine();
            if(input.equals("2.true"))
                InsertBox.display(true);
            else
                InsertBox.display(false);
            System.out.println(input);
        }catch (IOException e){
            System.out.println("Zle pobrano dane przy wstawianiu do drzewa");
        }

    }

    /**
     * Funkcja praktycznie bliźniacza do poprzedniej tylko przekazuje polecenie kasowania elemntu zamiast dodania.
     */
    public void deleteKeyButtonClicked(){
        int treeNumber = table.getSelectionModel().getSelectedItem().getNumber();
        String value = keyTextField.getText();

        out.println("3." + treeNumber + "." + value);

        try{
            String input = in.readLine();
            if(input.equals("3.true"))
                DeleteBox.display(true);
            else
                DeleteBox.display(false);
            System.out.println(input);
        }catch (IOException e){
            System.out.println("Zle pobrano dane przy usuwanie elementu drzewa");
        }



    }

    /**
     * Funkcja przekazuje do sewera kod "1.numer_drzewa" i otrzymuje w wiadomości elementy drzewa w kolejności
     * niemalejącej, które potem wyświetla w nowym okienku.
     */
    public void drawButtonClicked(){
        int treeNumber = table.getSelectionModel().getSelectedItem().getNumber();

        out.println("1." + treeNumber);


        try{
            String input = in.readLine();
            DrawBox.display(input);

        }catch (IOException e){
            System.out.println("Zle pobrano dane przy wyświetlaniu drzewa");
        }

    }

    /**
     * Przekazuje do serwera kod o utworzenie nowego drzewa odpowiedniego typu oraz wrzuca informacje
     * do listy drzew o posiadaniu takiego drzewa
     */
    public void addButtonClicked() {
        String numberInString = Integer.toString(numberOfTrees);
        switch (typeComboBox.getValue()) {
            case "Integer":
                TreeInfo integerTree = new TreeInfo();
                integerTree.setTreeName(nameInput.getText());
                integerTree.setTypeName("Integer");
                integerTree.setNumber(numberOfTrees);
                table.getItems().add(integerTree);

                out.println("0." + numberInString + ".Integer");

                nameInput.clear();
                typeComboBox.setPromptText("");
                break;
            case "Double":
                TreeInfo DoubleTree = new TreeInfo();
                DoubleTree.setTreeName(nameInput.getText());
                DoubleTree.setTypeName("Double");
                DoubleTree.setNumber(numberOfTrees);
                table.getItems().add(DoubleTree);

                out.println("0." + numberInString + ".Double");

                nameInput.clear();
                typeComboBox.setPromptText("");
                break;
            case "String":
                TreeInfo StringTree = new TreeInfo();
                StringTree.setTreeName(nameInput.getText());
                StringTree.setTypeName("String");
                StringTree.setNumber(numberOfTrees);
                table.getItems().add(StringTree);

                out.println("0." + numberInString + ".String");

                nameInput.clear();
                typeComboBox.setPromptText("");
                break;
            default:
                System.out.println("Cos z kliknieciem poszlo zle");
                break;
        }

        try{
            String input = in.readLine();
            if(input.equals("0.true"))
                System.out.println("Operacja nowego drzewa powiodła się");
            else
                System.out.println("Operacja nowego drzewa nie powiodla sie");
            System.out.println(input);
        }catch (IOException e){
            System.out.println("Zle pobrano dane przy wstawianiu drzewa");
        }
        numberOfTrees++;
    }

    /**
     * Funkcja usuwa informacje z listy drzew o zaznaczonym drzewie. To drzewo jednak zostaje na serwerze.
     */
    public void deleteButtomClicked(){
        ObservableList<TreeInfo> treeSelected, allTrees;
        allTrees = table.getItems();
        treeSelected = table.getSelectionModel().getSelectedItems();

        treeSelected.forEach(allTrees:: remove);

    }



}
