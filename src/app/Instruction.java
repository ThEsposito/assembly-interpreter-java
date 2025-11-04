package app;

public class Instruction implements Comparable<Instruction> {
    private int lineNumber;


    public int compareTo(Instruction other){
        return Integer.compare(lineNumber, other.lineNumber);
    }
}
