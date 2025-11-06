package datastructures;

// OBS: não devem ser permitidas linhas com número de linha igual
public class OrderedLL<T extends Comparable<T>> {
    private Node<T> head, tail;
    private int size;
    private Node<T> pWalks; //node?

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

    // RETORNA O INICIO DA LISTA
    public Node<T> getHead() {
        return head;
    }

    // RETORNA O TAIL DA LISTA
    public Node<T> getTail() {
        return tail;
    }

    // INSERE ELEMENTO EM ORDEM CRESCENTE (PRONTO??)
    public void insert(T element){ //pode chamar element de linha, instrucao sla?
        Node<T> novo = new Node<>(element); // encapsulando o elemento para tratar como node 
        pWalks = this.head; // inicia o pWalks no segundo node
        Node<T> anterior = null;

        if(head == null){ // caso a lista esteja vazia ja incia com o novo elemento
            head = tail = novo;
            size++;
            return;
        }

    	if(novo.getData().compareTo(head.getData()) < 0){ // se for menor que o primeiro node
             novo.setNext(this.head);
             this.head = novo; // atualiza o head
	    }
        
        // anda enquanto não chegar na posição correta (ordem crescente)
        while(pWalks.getNext() != null && novo.getData().compareTo(pWalks.getData()) > 0){ 
            anterior = pWalks; // avanca o anterior
            pWalks = pWalks.getNext(); //pWalks walks - fica uma "casa" a frente do anterior
        }

        novo.setNext(pWalks);
        anterior.setNext(novo);

        size+=1; //atualiza o tamanho
    }
}
