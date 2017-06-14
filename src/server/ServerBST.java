package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Projekt - Aplikacja klient-serwer obsługująca drzewa binarne
 *
 * ServerBST.java
 * Plik tworzy serwer BST, który oczekuje na połączenie, kiedy to nastąpi odbiera linie kodu.
 * Każdy kod ma zakodowane polecenie oraz wartości odzielone kropkami.
 * Polecenia:
 * 0 - tworzy nowe drzewo
 * 1 - zwraca zawartosc drzewa
 * 2 - dodaje element do drzewa
 * 3 - usuwa element z drzewa
 * 4 - wyszukuje elementu w drzewie
 *
 * Jesli kod to "test" to serwer  zwraca informacje o prawidłowym połączeniu.
 *
 *@author Szymon Wojtaszek
 *@version to oddania
 */

public class ServerBST {

    private static final int PORT_NUMBER = 9978;
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private static List<Tree>  list = new LinkedList<Tree>();

    public static void main(String[] args) {

        try (
                ServerSocket server = new ServerSocket(PORT_NUMBER)
        ) {
            while (true) {
                Socket client = server.accept();
                System.out.println("Server podloczono");
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                String line = "";
                int c;

                while ((line = in.readLine()) != null) {

                    if(line.equals("test")){
                        out.println("Test udany");
                        continue;
                    }
                    line = process(line);
                    out.println(line);

                }
                System.out.println("Server rozloczono");
                client.close();
                in.close();
                out.close();
            }
        } catch (Exception e) {
            System.out.println("OMG BLEDY WSZEDZIE");
            System.out.println(e);

        }
    }

    /**
     * Funckcja fromDot pobiera linie kodu, oraz zwraca fragment łańcucha znajdujący się po określonej kropce
     * Pomaga to, aby wydowybyć wartości z kodu.
     *
     * @param input
     * @param dotNumber
     * @return Fragment łańcucha z interesującą nas zawartością
     *
     */
    private static String fromDot(String input, int dotNumber){
        char table[] = input.toCharArray();


        int beginning = -1;
        int end = table.length;
        int n = 0;

        for(int i = 0; i < table.length; i++){

            if(table[i] == '.')
                n++;
            if(table[i] == '.' && n == dotNumber)
                beginning = i;
            if(n > dotNumber){
                end = i;
                break;
            }

        }

        char[] newTable = new char[end - beginning - 1];

        for(int i = 0; i < newTable.length; i++)
            newTable[i] = table[i + beginning + 1];
        return new String(newTable);


    }

    /**
     * Funkcja process bierze nasz kod i właściwą funkcję określoną w pierwszym fragmencie kodu.
     *
     * @param input
     * @return wartość wysyłana potem przez server, albo potwierdzenie wykonania polecenia, albo zawartość  drzewa
     */
    private static String process(String input){
        switch(Integer.parseInt(fromDot(input, 0))){
            case 0:
                return newTree(input);
            case 1:
                return showTree(input);
            case 2:
                return insert(input);
            case 3:
                return delete(input);
            case 4:
                return search(input);
            default:
                return null;

        }


    }

    /**
     * POLECENIE: 1
     * KOD: polecenie.numer_drzewa
     *
     * @param input
     * @return Zawartość drzewa o interesującym nas numerze
     */
    private static String showTree(String input){
        String feedback = "";

        feedback += list.get(Integer.parseInt(fromDot(input, 1))).inorderWalk();

        return feedback;
    }

    /**
     * POLECENIE: 0
     * KOD: polecenie.numer_drzewa.typ_nowego_drzewa
     *
     * @param input
     * @return "0.true/false"
     */
    private static String newTree(String input){
        String feedback = "0.";
        int index = Integer.parseInt(fromDot(input, 1));
        if(index > list.size())
            return feedback += "false";

        switch (fromDot(input, 2)){
            case "String":
                Tree<String> treeString = new Tree<>();
                treeString.setTypeName("String");
                list.add(index, treeString);
                return feedback += "true";
            case "Integer":
                Tree<Integer> treeInteger = new Tree<>();
                treeInteger.setTypeName("Integer");
                list.add(index, treeInteger);
                return feedback += "true";
            case "Double":
                Tree<String> treeDouble = new Tree<>();
                treeDouble.setTypeName("Double");
                list.add(index, treeDouble);
                return feedback += "true";
            default:
                return null;

        }

    }

    /**
     * POLECENIE: 2
     * KOD: polecenie.numer_drzewa.wstawiana_wartość
     *
     * @param input
     * @return "0.true/false"
     */
    private static String insert(String input) {
        String feedback = "2.";
        int index = Integer.parseInt(fromDot(input, 1));
        if(index > list.size())
            return feedback += "false";

        Tree tree = list.get(index);
        String value = fromDot(input, 2);

        switch (tree.getTypeName()){
            case "String":
                tree.insert(value);
                return feedback += "true";
            case "Integer":
                tree.insert(Integer.parseInt(value));
                return feedback += "true";
            case "Double":
                tree.insert(Double.parseDouble(value));
                return feedback += "true";
            default:
                return feedback += "false";
        }


    }

    /**
     * POLECENIE: 3
     * KOD: polecenie.numer_drzewa.usuwana_wartość
     *
     * @param input
     * @return "0.true/false"
     */
    //polecenie.numer_drzewa.usuwana_wartość
    private static String delete(String input) {
        String feedback = "3.";
        int index = Integer.parseInt(fromDot(input, 1));
        if(index > list.size())
            return feedback += "false";

        Tree tree = list.get(index);
        String value = fromDot(input, 2);

        switch (tree.getTypeName()){
            case "String":
                tree.delete(value);
                return feedback += "true";
            case "Integer":
                tree.delete(Integer.parseInt(value));
                return feedback += "true";
            case "Double":
                tree.delete(Double.parseDouble(value));
                return feedback += "true";
            default:
                return feedback += "false";
        }


    }

    /**
     * Funcja search sprawdza, czy interesujacy nas element jest w drzewiee
     * POLECENIE: 4
     * KOD: polecenie.numer_drzewa.szukana_wartość
     *
     * @param input
     * @return "0.true/false"
     */
    private static String search(String input){
        String feedback = "4.";
        int index = Integer.parseInt(fromDot(input, 1));
        if(index > list.size())
            return feedback += "false";

        Tree tree = list.get(index);
        String value = fromDot(input, 2);

        switch (tree.getTypeName()){
            case "String":
                return feedback += Boolean.toString(tree.search(value));
            case "Integer":
                return feedback += Boolean.toString(tree.search(value));
            case "Double":
                return feedback += Boolean.toString(tree.search(value));
            default:
                return feedback += "false";
        }

    }


}