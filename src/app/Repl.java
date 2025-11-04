package app;

import datastructures.OrderedLL;
import java.io.IOException;

public class Repl {
    private Registers regs;
    OrderedLL<Instruction> instructions;

    // Carrega o arquivo, transforma cada linha (de String pra Instruction) e seta
    // o atributo instructions (com as instrucoes carregadas)
    // IOException é uma exceção que vai ser lançada caso a gente não consiga ler/abrir o arquivo;
    public void load(String path) throws IOException {

    }

    // Lança a Exceção se não houver instrução (instructions.isEmpty())
    public void list() throws Exception {


    }

    // Roda. Exceção se a lista estiver vazia.
    public void run(OrderedLL<Instruction> sourceCode) throws Exception {


    }

    // CUIDADO AO INSERIR NUMERO DE LINHA QUE JA EXISTE!!!!
    // VOCE VAI ESQUECER DE MUDAR O ATRIBUTO LINENUMBER, FICANDO COM DOIS ELEMENTOS NA MESMA LINHA!
    public void insert(Instruction instruction, int line) throws IllegalArgumentException {

    }

    public void delete(int line){

    }

    public void delete(int startLine, int endLine){}

    // Salva o conteúdo da LL (atributo instructions) num arquivo.
    public void save(){}

    // Mesma coisa, mas especifica o path (que inclui o nome do arquivo)
    public void save(String path){}
}
