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
public class TreeInfo {
    private String treeName;
    private String typeName;
    private Integer number;

    TreeInfo(){
        typeName = "";
        typeName = "";
        number = 0;
    }

    public String getTreeName() {
        return treeName;
    }
    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }


}
