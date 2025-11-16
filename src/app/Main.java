package app;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] command;
        Repl repl = new Repl();
        Scanner sc = new Scanner(System.in);

        int lineNumber; // Alguns comandos vão usar. Acho melhor do que precisar reinicializar a variavel toda vez
        do {
            command = sc.nextLine().trim().replaceAll("\\s+", "").toUpperCase()
                    .split(" ");

            // Dava pra ir fazendo um monte de if (em cada case) pra conferir o tamanho do array!
            // Se houver excesso de parâmetros, a gente pode printar isso na tela.
            try {
                switch (command[0]) {
                    case "LOAD":
                        if(command.length < 2){
                            System.out.println("Too few arguments!");
                            continue;
                        } else if(command.length > 2){
                            System.out.println("Too many arguments!");
                            continue;
                        }
                        if(repl.isFileOpen() && repl.hasUnsavedChanges()) {
                            System.out.println("File '"+repl.getFileName()+"' is open!\n Do you want to save before exit?");
                            String answer = sc.nextLine().trim().toUpperCase();
                            if(answer.startsWith("Y")) {
                                repl.save();
                            }
                        }
                        try {
                            repl.load(command[1]);
                        } catch(IOException ioe){
                            System.out.println("An error occurred while opening the file: "+ioe.getMessage());
                        }
                        break;
                    case "LIST":
                        if(command.length > 1){
                            System.out.println("Too many arguments!");
                            continue;
                        }

                        // Verificar arquivo vazio??
                        if(!repl.isFileOpen()){
                            System.out.println("There's no file open! Type 'LOAD' to select one."); // Meu inglês é básico
                        } else {
                            repl.list();
                        }

                        break;
                    case "RUN":
                        try {
                            repl.run();
                        } catch(Exception e){
                            System.out.println(e.getMessage());
                        }// Tratar cada erro individualmente aqui! Registrador indefinido, por exemplo.
                        break;
                    case "INS":
                        lineNumber = Integer.parseInt(command[1]);

                        // *lógica pra parsear (converter) a instrução*
                        Instruction instruction = new Instruction();
                        if(repl.containsLine(lineNumber));
                        repl.insert(instruction, lineNumber);
                        break;
                    case "DEL":
                        if(command.length == 2){

                        } else if(command.length == 3){

                        } else {
                            System.out.println("Too much arguments!");
                        }
                        break;
                    case "SAVE":


                        break;
                    case "EXIT":
                        break;
                    default:
                        System.out.println("Invalid Command!");
                }
            } catch(NumberFormatException nfe){
                System.out.println("Invalid operator! Argument must be int.");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        } while (!command[0].equals("EXIT"));
    
    }
}