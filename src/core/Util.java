package core;

import datastructures.OrderedLL;

public class Util {
    public static int lineNumberToIdx(int lineNumber, OrderedLL<Instruction> instructions){
        if(instructions.isEmpty()) return -1;

        for(int i=0; i<instructions.getSize(); i++){
            if(instructions.get(i).getLineNumber() == lineNumber) return i;
        }
        return -1;
    }
}
