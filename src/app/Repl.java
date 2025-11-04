package app;

import datastructures.OrderedLL;
import java.io.IOException;

public class Repl {
    private Registers regs;
    private OrderedLL<Instruction> instructions;

    // Carrega o arquivo, transforma cada linha (de String pra Instruction) e seta
    // o atributo instructions (com as instrucoes carregadas)
    // IOException é uma exceção que vai ser lançada caso a gente não consiga ler/abrir o arquivo;
    public void load(String path) throws IOException {

    }

    // Lança a Exceção se não houver instrução (instructions.isEmpty())
    public void list() throws Exception {


    }

    // Roda. Exceção se a lista estiver vazia.
    public void run() throws Exception {


    }

    // CUIDADO AO INSERIR NUMERO DE LINHA QUE JA EXISTE!!!!
    // VOCE VAI ESQUECER DE MUDAR O ATRIBUTO LINENUMBER, FICANDO COM DOIS ELEMENTOS NA MESMA LINHA!
    // ACREDITO QUE ISSO DEVA SER IMPLEMENTADO NA CLASSE DA LINKEDLIST!!
    public void insert(Instruction instruction, int line) throws IllegalArgumentException {

    }

    public void delete(int line){

    }

    public void delete(int startLine, int endLine){}

    // Salva o conteúdo da LL (atributo instructions) num arquivo.
    public void save(){}

    // Mesma coisa, mas especifica o path (que inclui o nome do arquivo)
    public void save(String path){}

    public Registers getRegs() {
        return this.regs;
        // Talvez possa ser útil retornar uma cópia, já que ao retornar um array
        // podemos alterá-lo de fora (pois arrays são ponteiros)
    }

    public boolean containsLine(int lineNumber){
        return false;
    }
}
