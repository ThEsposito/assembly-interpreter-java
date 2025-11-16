package app;

import datastructures.OrderedLL;
import exceptions.UndefinedRegisterException;

import java.io.File;
import java.io.IOException;

public class Repl {
    private final Registers regs;
    private OrderedLL<Instruction> instructions; // É melhor uma lista de objetos ou de strings??
    private boolean hasUnsavedChanges;
    private File file;

    // E o construtor?? Já coloco um que receba o path e abre o arquivo?
    public Repl() {
        regs = new Registers();
        hasUnsavedChanges = false;
        file = null;
//        load(filePath); // Já instancia o File e a lista de instruções?
    }

    public boolean hasUnsavedChanges() {
        return this.hasUnsavedChanges;
    }

    public boolean isFileOpen() {
        return this.file != null;
    }

    public  boolean isFileEmpty() {
        return file.length() == 0;
    }

    // Carrega o arquivo, transforma cada linha (de String pra Instruction) e seta
    // o atributo instructions (com as instrucoes carregadas)
    // IOException é uma exceção que vai ser lançada caso a gente não consiga ler/abrir o arquivo;
    public void load(String path) throws IOException {
        if(isFileOpen()) {
            regs.clear();
        }
    }

    // Lança a Exceção se não houver instrução (instructions.isEmpty())
    public void list() throws Exception {
        if(instructions == null || instructions.isEmpty()) throw new Exception("Empty Code!");

    }

    // Roda.
    // Exceção se a lista estiver vazia.
    // Exceção se a instrução for desconhecida (isso a classe instruction pode lançar)
    // Exceção se não houver arquivo aberto
    public void run() throws UndefinedRegisterException, Exception {
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
    public void save(){

        this.hasUnsavedChanges = false;
    }

    // Mesma coisa, mas especifica o path (que inclui o nome do arquivo)
    public void save(String path){
        if(file.getPath().equals(path) || file.getAbsolutePath().equals(path)){
            this.hasUnsavedChanges = false;
        }
    }

    public Registers getRegs() {
        return this.regs;
        // Talvez possa ser útil retornar uma cópia, já que ao retornar um array
        // podemos alterá-lo de fora (pois arrays são ponteiros)
    }


    public boolean containsLine(int lineNumber){
        if(instructions.isEmpty()) return false;

        for(int i=0; i<instructions.getSize(); i++){
            if(instructions.get(i).getLineNumber() == lineNumber) return true;
        }
        return false;
    }

    public String getFileName() {
        if(file != null) return file.getName();
        return null; // Achar uma exceção para lançar aqui, ao invés de retornar nulo
    }
}
