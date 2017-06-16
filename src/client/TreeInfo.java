package client;
/**
 * Projekt - aplikacja klient-serwer obsługująca drzewa binarne.
 *
 * TreeInfo.java
 * Klasa przechowuje podstawowe informacje o stworzonych drzewach, które są wyświetlane w tableView klienta.
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
class TreeInfo {
    private String treeName;
    private String typeName;
    private Integer number;

    String getTreeName() {
        return treeName;
    }
    void setTreeName(String treeName) {
        this.treeName = treeName;
    }
    String getTypeName() {
        return typeName;
    }
    void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    Integer getNumber() {
        return number;
    }
    void setNumber(Integer number) {
        this.number = number;
    }


}
