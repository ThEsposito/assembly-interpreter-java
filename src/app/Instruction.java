package app;

public class Instruction implements Comparable<Instruction> {
    private String rawLine;
    private int lineNumber;
    private String opcode;
    private String arg1; // Mantive como string porque poderia ser um char(A-Z) ou um número imediato
    private String arg2;

    // Sobrecarreguei o construtor porque algumas instruções podem ter só 1 argumento
    public Instruction(String rawLine, int lineNumber, String opcode, String arg1) {
        this.rawLine = rawLine;
        this.lineNumber = lineNumber;
        this.opcode = opcode;
        this.arg1 = arg1;
    }

    public Instruction(String rawLine, int lineNumber, String opcode, String arg1, String arg2) {
        this(rawLine, lineNumber, opcode, arg1);
        this.arg2 = arg2;
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
}
