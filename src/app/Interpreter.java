package app;

import datastructures.OrderedLL;

public class Interpreter{
    public void execute(OrderedLL<Instruction> instructions){
        Registers regs = new Registers(); // Inicializa os registradores

        Node<Instruction> current = instructions.getHead();

        while(current != null){
            Instruction instr = current.getData();
            Node<Instruction> next = current.getNext(); // Nó padrão para a próxima iteração
            boolean jumped = false; // Flag para controlar se um salto ocorreu
            try{
                switch(instr.getOpcode()){
                    case MOVE:
                        executeMov(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case INC:
                        executeInc(regs, instr.getArg1());
                        break;
                    case DEC:
                        executeDec(regs, instr.getArg1());
                        break;            
                    case ADD:
                        executeAdd(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case SUB:
                        executeSub(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case MUL:
                        executeMul(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case DIV:
                        executeDiv(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case OUT:
                        executeOut(regs, instr.getArg1());
                        break;
                    case JNZ:
                    // Este método deve:
                    // 1. Verificar o valor de arg1 em 'regs'.
                    // 2. Se NÃO for zero, ele deve encontrar o Nó da linha (arg2) na lista.
                    // 3. Se encontrar, ele RETORNA esse Nó.
                    // 4. Se for zero (ou não encontrar), ele retorna null (sem salto).
                    
                    // Nota: ele precisa da lista inteira 'instructions' para procurar a linha
                    Node<Instruction> jumpTarget = executeJnz(regs, instr.getArg1(), instr.getArg2(), instructions);
                    
                    if (jumpTarget != null) {
                        nextNode = jumpTarget; // Sobrescreve o próximo nó!
                    }
                    // Se jumpTarget for null, ele não faz nada, e o loop
                    // simplesmente usará o 'nextNode' padrão (current.getNext()).
                    break;
                    default:
                        throw new IllegalArgumentException("Unknown opcode: " + instr.getOpcode()); // operacao desconhecida
                }
            } catch(Exception e){
                System.out.println("Error at line " + instr.getLineNumber()+": "+ e.getMessage());
                return; // Encerra a execução ao encontrar um erro
            }
            current = current.getNext();
        }
    }
}
