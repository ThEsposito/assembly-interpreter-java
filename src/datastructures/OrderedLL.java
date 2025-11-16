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

    public boolean remove(T element){
        if(isEmpty()){
            return false;
        }

        pWalks = head;
        Node<T> anterior = null;

        while(pWalks != null && pWalks.getData().compareTo(element) != 0){
            anterior = pWalks;
            pWalks = pWalks.getNext();
        }

        if(pWalks == null){ // elemento nao encontrado
            return false;
        }

        if(pWalks == head){ // se for o primeiro elemento
            head = head.getNext();
        } else {
            anterior.setNext(pWalks.getNext());
        }

        size--;
        return true;
    }

//----------------------------------------------------------------------------------------------------------------------------------

    public void removeRange(T elem1, T elem2){ // CONFERIR SE ESTA CERTO
        if(head == null) return; // lista vazia

        Node<T> current = head;
        Node<T> start = null; // achar o elemento de start
        Node<T> end = null; // achar o elemento de end

        while(current != null && start == null){
            if(current.getData().equals(elem1)){
                start = current;
            }else{
                current = current.getNext();
            }
        }
        // se não encontrou o primeiro, sai
        if (start == null) return;

        end = start;
        while(end != null && !end.getData().equals(elem2)){
            end = end.getNext();
        }
        // se não encontrou o segundo, sai
        if (end == null) return;

        // agora temos: start (início da remoção), end (fim da remoção)
        // precisamos achar o nó anterior ao start
        Node<T> anterior = null;
        Node<T> p = head;
        while (p != null && p != start) {
            anterior = p;
            p = p.getNext();
        }

        // liga o anterior diretamente ao nó após o último
        if (anterior != null){
            anterior.setNext(end.getNext());
        }else{
            head = end.getNext(); // se o primeiro nó removido era o head
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public Node<T> search(T element){
        Node<T> current = this.getHead();

        while(current != null){
            if(current.getData().compareTo(element) != 0){ // se nao achar o elemento
                current = current.getNext();
            }else{ // se achar
                return current;
            }
        }

        return null;
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void printAll(){
        Node<T> current = this.getHead();
        int pageSize = 20; // numero de linhas por pagina
        int pageCont = 0; // contador para as linhas ja impressas

        if(current == null){ // edge case
            System.out.println("Lista Vazia!");
            return;
        }

        while(current != null){
            System.out.println(current.getData().toString());
            pageCont++;
            current = current.getNext();

            if(pageCont == pageSize){
                System.out.println("Pressione ENTER para continuar ou S para sair)");
                try{
                    char c = (char) System.in.read();
                    if(c == 'S' || c == 's'){ // sai do loop
                        break;
                    }
                    pageCont = 0; // reseta o contador de linhas
                }catch (Exception e){
                    e.printStackTrace();
                }
            } // volta para o comeco do while
        }
        if(pageCont > 0){
            System.out.println("...");
        }
        return;
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void clear(){ // autoexplicativo
        head = tail = null;
        size = 0;
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public boolean contains(T element){ // verifica se a linha existe
        Node<T> current = this.getHead();

        while(current != null){
            if(current.getData().compareTo(element) != 0){ // se nao achar o elemento
                current = current.getNext();
            }else{ // se achar
                return true;
            }
        }

        return false;
    }


} // fim da classe*
    

