package app;

public class Instruction implements Comparable<Instruction> {
    private int lineNumber;

    public int compareTo(Instruction other){
        return Integer.compare(lineNumber, other.lineNumber);
    }

    public boolean equals(Instruction instruction) {
        return lineNumber == instruction.getLineNumber();
    }

    // Isso deveria estar na main??
//    public Instruction parseInstruction(String s){}

    public int getLineNumber() {
        return lineNumber;
    }
}
