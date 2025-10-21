package datastructures;

import exceptions.EmptyListException;

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
        if(isEmpty()) throw new EmptyListException();
        return this.head;
    }

    public Node<T> getLast() {
        if(isEmpty()) throw new EmptyListException();

        Node<T> aux = head;

        while(aux.getNext() != null) {
            aux = aux.getNext();
        }
        return aux;
    }
    // TODO: test
    public Node<T> get(int idx){
        if(isEmpty()) throw new EmptyListException();
        if(idx < 0 || idx >= size) throw new IndexOutOfBoundsException();

        Node<T> aux = head;
        for(int i=0; i<idx; i++){
            aux = aux.getNext();
        }
        return aux;
    }

    public void addFirst(T e){
        head = new Node<>(e, head);
        size++;
    }

    public void addLast(T e){
        if(isEmpty()) this.head = new Node<>(e);
        else {
            Node<T> aux = head;

            while(aux.getNext() != null){
                aux = aux.getNext();
            }

            aux.setNext(new Node<>(e));
        }
        size++;
    }

    // TODO: test
    public void insert(T e, int idx){
        if(idx < 0 || idx > size) throw new IndexOutOfBoundsException();

        if(idx == size) addLast(e);
        else if(idx == 0) addFirst(e);
        else {
            Node<T> aux = head;
            for(int i=0; i<idx-1; i++){
                aux = aux.getNext();
            }
            Node<T> newElement = new Node<>(e, aux.getNext());
            aux.setNext(newElement);

            size++;
        }
    }

    // TODO: Realmente necessário lançar exceção aqui?
    public void removeFirst() {
        if(isEmpty()) throw new EmptyListException();

        head = head.getNext(); // Still works if head.getNext is null
        size--;
    }

    public void removeLast() {
        if(isEmpty()) throw new EmptyListException();

        Node<T> aux = head;

        while(aux.getNext().getNext() != null) {
            aux = aux.getNext();
        }
        aux.setNext(null);
        size--;
    }

    public void remove(T e){
        if(isEmpty()) throw new EmptyListException();
        Node<T> aux = head;
        Node<T> previous = null;

        while(aux != null && !aux.getData().equals(e)){
            previous = aux;
            aux = aux.getNext();
        }
        if(aux == null) return; // Not found

        if(head == aux) {
            head = head.getNext();
        } else {
            previous.setNext(aux.getNext());
        }
        size--;
    }

    public void removeAt(int idx){
        if(isEmpty()) throw new EmptyListException();
        if(idx < 0 || idx >= size) throw new IndexOutOfBoundsException();

        if(idx == 0) removeFirst();
        else if (idx == size-1) removeLast();
        else {
            Node<T> aux = head;
            for(int i=0; i<idx-1; i++){
                aux = aux.getNext();
            }
            // TODO: test! Essa parte pode causar erro, caso aux seja o penúltimo
            aux.setNext(aux.getNext().getNext());
            size--;
        }
    }

    public void clear() {
        head = null;
        size = 0;
    }
}