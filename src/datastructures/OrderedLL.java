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

    // INSERE ELEMENTO EM ORDEM CRESCENTE
    public void insert(T element){ //pode chamar element de linha, instrucao sla?
	pWalks = head;
    	while(!(element <= pWalks.getNext())){ // anda enquanto não chegar na posição correta (ordem crescente)
		pWalks = pWalks.getNext(); //pWalks walks
	}
	// resto
        size+=1; //atualiza o tamanho
    }
}
