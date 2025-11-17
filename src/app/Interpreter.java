package app;

import datastructures.Node;
import datastructures.OrderedLL;

public class Interpreter{
    Registers regs;

    public Interpreter() {
        this.regs = new Registers();
    }

    public void execute(OrderedLL<Instruction> instructions){
        Node<Instruction> current = instructions.getHead(); // É uma má prática expor os Nodes assim??

        while(current != null){
            Instruction instr = current.getData();
            Node<Instruction> next = current.getNext(); // Nó padrão para a próxima iteração
            boolean jumped = false; // Flag para controlar se um salto ocorreu
            /*
            Edge cases:
             - Excesso/falta de argumentos
             - Registrador indefinido (não atribuído)
             - Valor imediato (inteiro) no argumento X (é proibido)
             - Registradores com mais de um caractere (!Util.isNumber(arg2) && arg2.length() > 1);

             Ideias:
              - Ao invés de iterar sobre os nodes, utilizar um for (e reatriuir o 'i' no jnz se necessário)
              - Passar a instrução para cada método, ao invés dos args (permitiria melhor validação) (um chute)
              - Um método só pra validar??? Não sei como fica mais bonito
              -
             */

            try{
                switch(instr.getOpcode()){
                    case "MOVE":
                        executeMov(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case "INC":
                        executeInc(regs, instr.getArg1());
                        break;
                    case "DEC":
                        executeDec(regs, instr.getArg1());
                        break;            
                    case "ADD":
                        executeAdd(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case "SUB":
                        executeSub(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case "MUL":
                        executeMul(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case "DIV":
                        executeDiv(regs, instr.getArg1(), instr.getArg2());
                        break;
                    case "OUT":
                        executeOut(regs, instr.getArg1());
                        break;
                    case "JNZ":
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

        regs.clear(); // Reseta os registradores. Lembrar de fazer isso ao lançar qquer exceção
    }
}
