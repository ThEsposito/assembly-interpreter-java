package datastructures;

public class LinkedList<T> {
    private Node<T> head;
    private int size;

    public LinkedList(){
        this.head = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0; // could be: head == null
    }

    public int getSize() {
        return this.size;
    }

    public Node<T> getFirst() {
        return this.head;
    }

    public Node<T> getLast() {
        Node<T> aux = head;
        
        while(aux.getNext() != null) {
            aux = aux.getNext();
        }

        return aux;
    }



}
