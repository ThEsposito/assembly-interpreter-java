package datastructures;

// OBS: não devem ser permitidas linhas com número de linha igual
public class OrderedLL<T extends Comparable<T>> {
    private Node<T> head, tail;
    private int size;

    public OrderedLL(){
        head = tail = null;
        size = 0;
    }

    // CONFERE SE A LISTA ESTA VAZIA
    public boolean isEmpty(){
        return head == null;
    }

    // RETORNA O TAMANHO DA LISTA
    public int getSize(){
        return size;
    }

    public Node<T> getHead() {
        return head;
    }

}
