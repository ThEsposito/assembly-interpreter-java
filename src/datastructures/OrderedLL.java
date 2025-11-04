package datastructures;

public class OrderedLL<T extends Comparable<T>> {
    private Node<T> head, tail;
    private int size;

    public OrderedLL(){
        head = tail = null;
        size = 0;
    }
}
