package core;

// Poderia ser uma record
public class Instruction implements Comparable<Instruction> {
    private final String rawLine;
    private final int lineNumber;
    private final String opcode;
    private final String arg1; // Mantive como string porque poderia ser um char(A-Z) ou um número imediato
    private final String arg2;
    private final String trash; // Usado para determinar linhas inválidas
    // Escolhemos essa abordagem porque, como o projeto é também um "editor de código", os erros também
    // ser mapeados para a lista (permitindo futuras alterações) e lançados somente durante a execução

    public Instruction(String rawLine, int lineNumber, String opcode, String arg1, String arg2, String trash) {
        this.rawLine = rawLine;
        this.lineNumber = lineNumber;
        this.opcode = opcode;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.trash = trash;
    }

    public int compareTo(Instruction other){
        return Integer.compare(lineNumber, other.lineNumber);
    }

    public boolean equals(Instruction instruction) {
        return lineNumber == instruction.getLineNumber();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getRawLine() {
        return rawLine;
    }

    public String getOpcode() {
        return opcode;
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public String getTrash() {
        return trash;
    }

    @Override
    public String toString() {
        return this.getRawLine();
    }
}
