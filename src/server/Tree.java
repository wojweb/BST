package server;

/**
 * Projekt - Aplikacja klient-serwer obsługująca drzewa binarne
 *
 * Tree.java
 *
 * Plik w miarę klasycznie implementuję drzewa binarne z podstawowymi funkcjami.
 * Przewiduje się, że drzewa moga być w typach String, Integer i Double.
 * Funkcjonowanie klasy jest dość oczywiste to nie będę się rozwodził nad konkretnymi funkcjami.
 *
 * @author Szymon Wojtaszek
 * @version do oddania
 * @param <T> String/Integer/Double
 */

public class Tree<T extends Comparable<T>> {
    private Integer quantity;
    private String typeName;

    private String elements;

    Node rootNode;

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    class Node {
        private T data;
        private Node leftChild;
        private Node rightChild;

        public T getData() {
            return data;
        }
        public void setData(T data) {
            this.data = data;
        }
        public Node getLeftChild() {
            return leftChild;
        }
        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }
        public Node getRightChild() {
            return rightChild;
        }
        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        Node(T data){
            this.data = data;

        }
    }

    public String inorderWalk(){
        elements = "";
        inorderWalkRec(rootNode);
        return elements;
    }
    private void inorderWalkRec(Node x){
        if(x != null){
            inorderWalkRec(x.getLeftChild());
            elements += x.getData().toString() + ", ";
            inorderWalkRec(x.getRightChild());
        }
    }

    public boolean search(T data){
        if(searchRec(rootNode, data) == null)
            return false;
        else
            return true;

    }

    private Node searchRec(Node x, T data){
        if(x == null || data.toString().compareTo(x.getData().toString()) == 0)
            return x;
        if(data.toString().compareTo(x.getData().toString()) < 0)
            return searchRec(x.getLeftChild(), data);
        else
            return searchRec(x.getRightChild(), data);
    }

    public void insert (T newData){
        quantity++;
        Node y = null;
        Node x = rootNode;

        while(x != null){
            y = x;
            if(newData.toString().compareTo(x.getData().toString()) < 0)
                x = x.getLeftChild();
            else
                x = x.getRightChild();
        }

        if(y == null)
            rootNode = new Node(newData);
        else if(newData.toString().compareTo(y.getData().toString()) <= 0)
            y.setLeftChild(new Node(newData));
        else
            y.setRightChild(new Node(newData));



    }

    /**
     * @deprecated Nie użyłem w kodzie
     * @param x
     * @return
     */
    public Node successor(Node x){
        if(x.getRightChild() != null){
            while(x.getLeftChild() != null)
                x = x.getLeftChild();
            return x;
        }
        Node succ = null;
        Node root = rootNode;
        while(root != null){
            if(x.getData().toString().compareTo(root.getData().toString()) < 0){
                succ = root;
                root = root.getLeftChild();
            }else if(x.toString().compareTo(root.getData().toString()) > 0){
                succ = root;
                root = root.getRightChild();
            }else
                break;
        }
        return succ;
    }
    private Node min(Node root){
        while(root.getLeftChild() != null)
            root = root.getLeftChild();
        return root;
    }
    public void delete(T key){
        rootNode = deleteRec(rootNode, key);
    }

    private Node deleteRec(Node root, T key){
        if(root == null)
            return root;

        if(key.toString().compareTo(root.getData().toString()) < 0)
            root.setLeftChild(deleteRec(root.getLeftChild(), key));
        else if(key.toString().compareTo(root.getData().toString()) > 0)
            root.setRightChild(deleteRec(root.getRightChild(), key));
        else{
            if(root.getLeftChild() == null)
                return root.getRightChild();
            else if(root.getRightChild() == null)
                return root.getLeftChild();

            root.setData(min(root.getRightChild()).getData());
            root.setRightChild(deleteRec(root.getRightChild(), root.getData()));
        }
        return root;

    }

    Tree(){
        quantity = 0;
    }

}
