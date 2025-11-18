package datastructures;

public class OrderedLL<T extends Comparable<T>> {
    private Node<T> head;
    private int size;

    public OrderedLL(){
        head = null;
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

    public T get(int idx) throws IndexOutOfBoundsException {
        if(idx<0 || idx >= size) throw new IndexOutOfBoundsException("Index "+idx+" out of bounds for length "+size);
        Node<T> current = head;

        for(int i=0; i<idx; i++) current = current.getNext();

        return current.getData();
    }

    // INSERE ELEMENTO EM ORDEM CRESCENTE
    public void insert(T element){
        Node<T> novo = new Node<>(element); // encapsulando o elemento para tratar como node

        if(isEmpty()){ // caso a lista esteja vazia ja incia com o novo elemento
            head = novo;
            size++;
            return;
        }

    	if(novo.getData().compareTo(head.getData()) < 0){ // se for menor que o primeiro node
            novo.setNext(this.head);
            this.head = novo; // atualiza o head

            size++;
            return;
	    }

        Node<T> pWalks = this.head.getNext(); // inicia o pWalks no segundo node
        Node<T> anterior = head;

        // anda enquanto não chegar na posição correta (ordem crescente)
        while(pWalks != null && element.compareTo(pWalks.getData()) > 0){
            anterior = pWalks; // avanca o anterior
            pWalks = pWalks.getNext(); //pWalks walks - fica uma "casa" a frente do anterior
        }

        anterior.setNext(novo);
        novo.setNext(pWalks);

        size+=1; //atualiza o tamanho
    }

    public boolean remove(T element){
        if(isEmpty()){
            return false;
        }

        Node<T> pWalks = head;
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

    public void removeAt(int idx) throws IndexOutOfBoundsException {
        if(idx<0 || idx>=size) throw new IndexOutOfBoundsException("Index "+idx+" out of bounds for length "+size);

        if(idx == 0){
            head = head.getNext();
            size--;

            return;
        }

        Node<T> current = head;

        for(int i=0; i<idx-1; i++) current = current.getNext();

        current.setNext(current.getNext().getNext()); // NullPointer?

        size--;
    }

//----------------------------------------------------------------------------------------------------------------------------------

    public void removeRange(int start, int end) {
        if (isEmpty()) return;
        if (start < 0 || start >= size) throw new IndexOutOfBoundsException("Index " + start + " out of bounds for length " + size);

        if (end < 0 || end >= size) throw new IndexOutOfBoundsException("Index " + end + " out of bounds for length " + size);

        if (start > end) throw new IllegalArgumentException("Start index (" + start + ") must be <= end index (" + end + ").");

        if (start == 0) {
            Node<T> current = head;

            for (int i = 0; i < end; i++) {
                current = current.getNext();
            }

            head = current.getNext();

            size -= (end - start + 1);
            return;
        }

        Node<T> beforeStart = head;
        for (int i = 0; i < start - 1; i++) {
            beforeStart = beforeStart.getNext();
        }

        Node<T> endNode = beforeStart.getNext();
        for (int i = start; i < end; i++) {
            endNode = endNode.getNext();
        }

        Node<T> afterEnd = endNode.getNext();
        beforeStart.setNext(afterEnd);

        size -= (end - start + 1);
    }

    public void removeRange(T elem1, T elem2) throws Exception {
        if(isEmpty()) throw new Exception("Empty List! There's no element to remove.");

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
        if (start == null) throw new Exception("Start not found: "+elem1);

        end = start;
        while(end != null && !end.getData().equals(elem2)){
            end = end.getNext();
        }
        // se não encontrou o segundo, sai
        if (end == null) throw new Exception("End not found: "+elem2);


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
        head = null;
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