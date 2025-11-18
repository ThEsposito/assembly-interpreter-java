package app;

import datastructures.OrderedLL;
import exceptions.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] command;
        Repl repl = new Repl();
        Scanner sc = new Scanner(System.in);

        int lineNumber; // Alguns comandos vão usar. Acho melhor do que precisar reinicializar a variavel toda vez
        do {
            System.out.print("> ");
            String line = sc.nextLine().trim().replaceAll("\\s+", " ");
            command = line.split(" ");
            command[0] = command[0].toUpperCase();

            switch (command[0]) {
                case "LOAD":
                    if(command.length != 2) {
                        System.out.println("Error: Incorrect number of arguments for LOAD!");
                        continue;
                    }

                    if(repl.isFileOpen() && repl.hasUnsavedChanges()) {
                        System.out.println("File '"+repl.getFileName()+"' is open!\n Do you want to save before exit? [Y/n]\n> ");
                        String answer = sc.nextLine().trim().toUpperCase();
                        if(answer.startsWith("Y")) {
                            try {
                                repl.save();
                            } catch (IOException ioe){
                                System.out.println("An error occurred while opening the file: "+ioe.getMessage());
                            }
                        }
                    }
                    try {
                        repl.load(command[1]);
                    } catch(IOException ioe){
                        System.out.println("An error occurred while opening the file: "+ioe.getMessage());
                    } catch(ParseException pe){
                        System.out.println("Warning: "+pe.getMessage());
                    }
                    break;

                case "LIST":
                    if(command.length > 2){
                        System.out.println("Error: No argument is required for LIST!");
                        continue;
                    }

                    if(!repl.isFileOpen()){
                        System.out.println("Error: There's no file open! Type 'LOAD <path>' to select one."); // Meu inglês é básico
                    } else if(repl.isCodeEmpty()){
                        System.out.println("Error: empty source code! There's nothing to list.");
                    } else {
                        repl.list();
                    }
                    break;

                case "RUN":
                    if(command.length > 1) {
                        System.out.println("Error: No argument is required for RUN!");
                        continue;
                    }
                    if(!repl.isFileOpen()){
                        System.out.println("Error: There's no file open! Type 'LOAD <path>' to select one."); // Meu inglês é básico
                        continue;
                    }

                    if(repl.isCodeEmpty()){
                        System.out.println("Empty source code! There's nothing to run.");
                        continue;
                    }

                    try {
                        repl.run(); // Todas as validações serão feitas pelo interpretador
                    } catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }// Tratar cada erro individualmente aqui! Registrador indefinido, por exemplo.
                    break;

                case "INS":
                    if(command.length < 4) {
                        System.out.println("Error: Incorrect number of arguments for LOAD!");
                        continue;
                    }
                    if(!repl.isFileOpen()) {
                        System.out.println("Error: There's no file open! Type 'LOAD <path>' to select one.");
                        continue;
                    }

                    try {
                        int newLineNumber = Integer.parseInt(command[1]);
                        boolean containsNewLine = repl.containsLine(newLineNumber);
                        String newLine = "";
                        for(int i=1; i<command.length; i++){
                            newLine += command[i] + ' ';
                        }
                        repl.insert(newLine);
                        if(containsNewLine){ // Tá dando erro aqui! Fala q atualizou uma linha q não existia
                            System.out.println("Line " + newLineNumber + " updated successfully!");
                        } else {
                            System.out.println("Line " + newLineNumber + " inserted successfully!");
                        }
                    } catch (ParseException pe) {
                        System.out.println("Error: "+ pe.getMessage());
                    } catch (NumberFormatException nfe){
                        System.out.println("Error: Malformed line! Must start with a number: "+command[1]);
                    }

                    break;
                case "DEL":
                    try {
                        if (command.length == 2) {
                            int deletionLine = Integer.parseInt(command[1]);
                            if(repl.delete(deletionLine)) {
                                System.out.println("Line " + line + " removed.");
                            } else {
                                System.out.println("Line " + line + " not found.");
                            }

                        } else if (command.length == 3) {
                            int start = Integer.parseInt(command[1]);
                            int end = Integer.parseInt(command[2]);
                            OrderedLL<Integer> deletions = repl.delete(start, end);

                            System.out.print("Lines removed: [ ");
                            for(int i=0; i<deletions.getSize(); i++){
                                System.out.print(deletions.get(i).toString() + ' ');
                            }
                            System.out.println("]");
                        } else {
                            System.out.println("Error: Expected 1 or 2 args for DEL.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Line must be a number.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "SAVE":
                    try {
                        if (command.length == 1) repl.save();
                        else if(command.length == 2) repl.save(command[1]);
                        else {
                            System.out.println("Error: Incorrect number of arguments for SAVE");
                        }
                    } catch(IOException ioe){
                        System.out.println("Error: "+ ioe.getMessage());
                    }
                    break;
                case "EXIT":
                    break;
                default:
                    System.out.println("Error: Invalid Command!");
            }
        } while (!command[0].equals("EXIT"));
    
    }
}