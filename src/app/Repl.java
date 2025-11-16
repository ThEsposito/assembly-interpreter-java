package app;

import datastructures.OrderedLL;
import exceptions.UndefinedRegisterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Repl {
    private Interpreter interpreter;
    private OrderedLL<Instruction> instructions;
    private boolean hasUnsavedChanges;
    private File file;

    // E o construtor?? Já coloco um que receba o path e abre o arquivo?
    public Repl() {
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
    public void load(String path) throws FileNotFoundException, IOException {
        File newFile = new File(path);
        if(!newFile.exists()) throw new FileNotFoundException("File "+newFile.getPath()+" not found!");

        this.hasUnsavedChanges = false;
        this.file = newFile;
        instructions.clear();

        Scanner fileScanner = new Scanner(newFile);
        while(fileScanner.hasNext()){
            Instruction inst = InstructionParser.parse(fileScanner.nextLine());
            // Avaliar também o tratamenmto/lançamento de exceções aqui
            instructions.insert(inst);
        }
        fileScanner.close();
    }

    // Lança a Exceção se não houver instrução (instructions.isEmpty())
    public void list() throws Exception {
        if(instructions == null || instructions.isEmpty()) throw new Exception("Empty file!");

        for(int i=0; i<instructions.getSize(); i++) {
            System.out.println(instructions.get(i).getRawLine());
        }
    }

    // Roda.
    // Exceção se a lista estiver vazia.
    // Exceção se a instrução for desconhecida (isso a classe instruction pode lançar)
    // Exceção se não houver arquivo aberto
    public void run() throws UndefinedRegisterException, IOException {
        if (instructions.isEmpty()) throw new IOException("Empty file!");

        interpreter.execute(this.instructions); // Tratar exceções?? Ou só delego isso pra main?
    }

    // CUIDADO AO INSERIR NUMERO DE LINHA QUE JA EXISTE!!!!
    // VOCE VAI ESQUECER DE MUDAR O ATRIBUTO LINENUMBER, FICANDO COM DOIS ELEMENTOS NA MESMA LINHA!
    // ACREDITO QUE ISSO DEVA SER IMPLEMENTADO NA CLASSE DA LINKEDLIST!!
    public void insert(String rawLine) throws IllegalArgumentException {
        Instruction instr = InstructionParser.parse(rawLine);

        int idx = lineNumberToIdx(instr.getLineNumber());
        if(idx != -1){
            instructions.removeAt(idx);
        }

        instructions.insert(instr);
        this.hasUnsavedChanges = true;
    }

    public boolean delete(int lineNumber) {
        if(instructions.isEmpty()) return false;

        int removeIdx = -1;
        for(int i=0; i<instructions.getSize(); i++){
            if(instructions.get(i).getLineNumber() == lineNumber){
                removeIdx = i;
                break;
            }
        }
        if(removeIdx == -1) return false;

        instructions.removeAt(removeIdx);
        return true;
    }

    public void delete(int startLine, int endLine){
        int startIdx = lineNumberToIdx(startLine);
        int endIdx = lineNumberToIdx(endLine);

        if(startIdx == -1){
            System.out.println("Line "+startLine+" not found"); // Exceção, print ou retorno boolean??
            return;
        }
        if(endIdx == -1){
            System.out.println("Line "+endLine+" not found"); // Exceção, print ou retorno boolean??
            return;
        }

        instructions.removeRange(startIdx, endIdx);
    }

    public void save() throws IOException{
        save(this.file.getAbsolutePath());
        this.hasUnsavedChanges = false;
    }

    // Mesma coisa, mas especifica o path (que inclui o nome do arquivo)
    public void save(String path) throws IOException {
        if(instructions.isEmpty()) throw new IOException("Nothing to write! Empty File");
        if(this.file.getPath().equals(path) || this.file.getAbsolutePath().equals(path)){
            this.hasUnsavedChanges = false;
        }
        File newFile = new File(path);
        if(newFile.exists()) { // Essa validação deveria ficar aqui?
            System.out.println("This file already exists! Do you want to override it? [Y/n]");
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine().toUpperCase();
            sc.close();

            if (!answer.startsWith("Y")) {
                return;
            }
        }
        // Já sobrescreve aqui??????
        PrintWriter writer = new PrintWriter(newFile);

        for(int i=0; i<instructions.getSize(); i++){
            writer.println(instructions.get(i).getRawLine());
        }
        writer.close();
    }

    // Poderia reestruturar esse método para retornar o índice de uma vez.
    // Pensando em desempenho e redundância aqui
    public boolean containsLine(int lineNumber){
        return lineNumberToIdx(lineNumber) != 1;
    }

    public String getFileName() {
        if(file != null) return file.getName();
        return null; // Achar uma exceção para lançar aqui, ao invés de retornar nulo
    }

    private int lineNumberToIdx(int lineNumber){
        if(instructions.isEmpty()) return -1;

        for(int i=0; i<instructions.getSize(); i++){
            if(instructions.get(i).getLineNumber() == lineNumber) return i;
        }
        return -1;
    }
}
