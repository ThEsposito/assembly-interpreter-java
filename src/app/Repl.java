package app;

import core.Instruction;
import core.InstructionParser;
import core.Interpreter;
import core.Util;
import datastructures.OrderedLL;
import exceptions.InterpreterException;
import exceptions.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Repl {
    private final Interpreter interpreter;
    private final OrderedLL<Instruction> instructions;
    private boolean hasUnsavedChanges;
    private File file;

    public Repl(){
        hasUnsavedChanges = false;
        file = null;
        interpreter = new Interpreter();
        instructions = new OrderedLL<>();
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
    public void load(String path) throws IOException, ParseException {
        File newFile = new File(path);
        if(!newFile.exists()) throw new FileNotFoundException("File "+newFile.getPath()+" not found!");

        this.file = newFile;
        instructions.clear();

        Scanner fileScanner = new Scanner(newFile);
        while(fileScanner.hasNext()){
            Instruction inst = InstructionParser.parse(fileScanner.nextLine());

            if(containsLine(inst.getLineNumber())){
                this.delete(inst.getLineNumber());
            }
            instructions.insert(inst);
        }
        fileScanner.close();
        this.hasUnsavedChanges = false;
    }

    public void list()  {
        if(instructions.isEmpty()) return;

        Scanner sc = new Scanner(System.in);
        for(int i=1; i<=instructions.getSize(); i++) {
            if(i % 21 == 0){
                System.out.print("Press ANY KEY to continue or Q to quit: ");
                char answer = sc.next().trim().toUpperCase().charAt(0);
                if(answer == 'Q') break;
            }
            System.out.println(instructions.get(i-1).getRawLine());
        }
    }

    // Roda.
    // Exceção se a lista estiver vazia.
    // Exceção se a instrução for desconhecida (isso a classe instruction pode lançar)
    // Exceção se não houver arquivo aberto
    public void run() throws InterpreterException {
        interpreter.execute(this.instructions); // Tratar exceções?? Ou só delego isso pra main?
    }

    // CUIDADO AO INSERIR NUMERO DE LINHA QUE JA EXISTE!!!!
    // VOCE VAI ESQUECER DE MUDAR O ATRIBUTO LINENUMBER, FICANDO COM DOIS ELEMENTOS NA MESMA LINHA!
    // ACREDITO QUE ISSO DEVA SER IMPLEMENTADO NA CLASSE DA LINKEDLIST!!
    public void insert(String rawLine) throws ParseException {
        Instruction instr = InstructionParser.parse(rawLine);

        int idx = Util.lineNumberToIdx(instr.getLineNumber(), instructions);
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
        hasUnsavedChanges = true;
        return true;
    }

    public OrderedLL<Integer> delete(int startLine, int endLine) throws Exception {
        if(startLine > endLine) throw new Exception("Invalid range: "+startLine+ " to "+endLine);
        if(startLine == endLine) delete(startLine);
        int startIdx = Util.lineNumberToIdx(startLine, instructions);
        int endIdx = Util.lineNumberToIdx(endLine, instructions);

        if(startIdx == -1) throw new Exception("Line "+startLine+" unexists");
        if(endIdx == -1) throw new Exception("Line "+endLine+" unexists");

        instructions.removeRange(startIdx, endIdx);
        OrderedLL<Integer> LineDeletions = new OrderedLL<>();
        for(int i=startIdx; i<=endIdx; i++){
            LineDeletions.insert(instructions.get(i).getLineNumber());
        }
        instructions.removeRange(startIdx, endIdx);
        hasUnsavedChanges = true;

        return LineDeletions;
    }

    public void save() throws IOException{
        save(this.file.getAbsolutePath());
    }

    // Mesma coisa, mas especifica o path (que inclui o nome do arquivo)
    public void save(String path) throws IOException {
        if(instructions.isEmpty()) throw new IOException("Nothing to write! Empty File");
        if(this.file.getPath().equals(path) || this.file.getAbsolutePath().equals(path)){
            this.hasUnsavedChanges = false;
        }
        File newFile = new File(path);
        if(newFile.exists() && !newFile.getAbsolutePath().equals(file.getAbsolutePath())) { // Essa validação deveria ficar aqui? Isso aqui já sobrescreve?
            System.out.println("This file already exists! Do you want to override it? [Y/n]");
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine().toUpperCase();
            sc.close();

            if (!answer.startsWith("Y")) {
                System.out.println("Aborting operation!");
                return;
            }
        } else {
            newFile.createNewFile();
        }

        try(PrintWriter writer = new PrintWriter(newFile)) {
            for (int i = 0; i < instructions.getSize(); i++) {
                writer.println(instructions.get(i).getRawLine());
            }

            this.hasUnsavedChanges = false;
        }
    }

    public boolean isCodeEmpty(){
        return instructions.isEmpty();
    }

    public String getFileName() {
        if(file != null) return file.getName();
        return null; // Achar uma exceção para lançar aqui, ao invés de retornar nulo
    }

    public boolean containsLine(int lineNumber){
        return Util.lineNumberToIdx(lineNumber, instructions) != -1;
    }
}
