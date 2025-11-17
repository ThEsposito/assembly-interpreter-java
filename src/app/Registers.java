/*
Alunos:
Theo Espósito Simões Resende  RA: 10721356
Kauê Lima Rodrigues Meneses RA: 10410594
*/

package app;

import exceptions.UndefinedRegisterException;

public class Registers {
    private Integer[] values;

    public Registers(){
        values = new Integer[26];
    }

    public void attribute(char var, int valor) throws IllegalArgumentException {
        int idx = this.getIndex(var);

        this.values[idx] = valor;
    }

    public void clear(){
        values = new Integer[26];
    }
    
    public int getValor(char var) throws IllegalArgumentException, UndefinedRegisterException {
        int idx = this.getIndex(var);
        if(values[idx] == null) {
            char[] alfabeto = {'A','B','C','D','E','F','G','H','I','J','K','L',
                    'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
            throw new UndefinedRegisterException("Erro: registrador "+ alfabeto[idx] + " não definida.");
        }
        return this.values[idx];
    }

    public boolean existe(char var) throws IllegalArgumentException {
        int idx = this.getIndex(var);

        // Como é um vetor de Double (um wrapper) os valores são inicializados como null
        return values[idx] != null;
    }

    public void reset(){
        values = new Integer[26];
    }

    public String list(){
        if(this.isEmpty()) return "Nenhum registrador foi atribuído.";
        char[] alfabeto = {'A','B','C','D','E','F','G','H','I','J','K','L',
                'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

        StringBuilder lista = new StringBuilder();

        for(int i=0; i<values.length; i++){
            Integer value = values[i];
            if(value != null) {
                lista.append(alfabeto[i]).append(" = ").append(value).append('\n');
            }
        }
        return lista.toString();
    }

    private int getIndex(char var) throws IllegalArgumentException {
        var = Character.toUpperCase(var);

        // Usamos a tabela ASCII para pegar o índice pro nosso vetor:
        // A = 65 e Z = 90
        if(var < 'A' || var > 'Z') {
            throw new IllegalArgumentException("A entrada deve ser uma letra entre A e Z!!");
        }

        return var - 'A';
    }
    private boolean isEmpty(){
        for(Integer v : values){
            if (v != null) return false;
        }
        return true;
    }
}
