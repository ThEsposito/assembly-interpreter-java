package app;

import datastructures.Node;
import datastructures.OrderedLL;
import exceptions.InterpreterException;
import exceptions.UndefinedRegisterException;

public class Interpreter{
    public void executeMov(Registers regs, Instruction instr) throws InterpreterException {
        if(!(Registers.isRegister(instr.getArg1())))
            throw new InterpreterException("Arg 1 must be a register: " + instr.getRawLine());
        if(instr.getArg3() != null || instr.getArg2() == null || instr.getArg1() == null)
            throw new InterpreterException("Incorrect number of arguments for MOV!: "+instr.getRawLine());

        char arg1 = instr.getArg1().charAt(0);
        int arg2;
        if(Registers.isRegister(instr.getArg2())){ // se eh registrador, eh String
            if(!regs.exists(instr.getArg2().charAt(0)))
                throw new InterpreterException("Register "+instr.getArg2() + " is undefined!");
            arg2 = regs.getValue(instr.getArg2().charAt(0));
        } else if(Util.isNumber(instr.getArg2())){
            arg2 = Integer.parseInt(instr.getArg2());
        } else throw new InterpreterException("Unknow arg2 format for: "+instr.getRawLine());

        regs.attribute(arg1, arg2);
    }

    public void executeInc(Registers regs, Instruction instr) throws InterpreterException{
        if(instr.getArg1() == null || instr.getArg2() != null || instr.getArg3() != null)
            throw new InterpreterException("Incorrect number of arguments for INC!: "+instr.getRawLine());

        if(!(Registers.isRegister(instr.getArg1())))
            throw new InterpreterException("Arg 1 must be a register: " + instr.getRawLine());

        char arg1 = instr.getArg1().charAt(0);
        int newValueForReg = regs.getValue(arg1) + 1;
        regs.attribute(arg1, newValueForReg);
    }

    public void executeDec(Registers regs, Instruction instr) throws InterpreterException{
        if(instr.getArg1() == null || instr.getArg2() != null || instr.getArg3() != null)
            throw new InterpreterException("Incorrect number of arguments for DEC!: "+instr.getRawLine());

        if(!(Registers.isRegister(instr.getArg1()))) {
            throw new InterpreterException("Arg 1 must be a register: " + instr.getRawLine());
        }
        char arg1 = instr.getArg1().charAt(0);
        if(!regs.exists(arg1))
            throw new UndefinedRegisterException("Register "+ arg1 + " is undefined at: "+instr.getRawLine());

        regs.attribute(arg1, regs.getValue(arg1) - 1);
    }

    public void executeAdd(Registers regs, Instruction instr) throws InterpreterException{
        // TODO: *** AVALIAR ***

        if(instr.getArg1() == null || instr.getArg2() == null || instr.getArg3() != null)
            throw new InterpreterException("Incorrect number of arguments for ADD!: "+instr.getRawLine());

        if(!(Registers.isRegister(instr.getArg2())))
            throw new InterpreterException("Arg 1 must be a register for ADD: " + instr.getRawLine());

        if(!Registers.isRegister(instr.getArg2()) && !Util.isNumber(instr.getArg2())) // se nao eh registrador nem num
            throw new InterpreterException("Unknow arg2 format for: "+instr.getRawLine());

        char arg1 = instr.getArg1().charAt(0);
        char arg2 = instr.getArg2().charAt(0);
        
        int sum = regs.getValue(arg1) + regs.getValue(arg2);
        regs.attribute(arg1, sum);
    }

    public void executeSub(Registers regs, Instruction instr) throws InterpreterException {
        if(instr.getArg1() == null || instr.getArg2() == null || instr.getArg3() != null)
            throw new InterpreterException("Incorrect number of arguments for SUB!: "+instr.getRawLine());

        if(!Registers.isRegister(instr.getArg1()))
            throw new InterpreterException("Arg 1 must be a register for SUB: "+instr.getRawLine());
        char arg1 = instr.getArg1().charAt(0);
        int arg2;
        if(Util.isNumber(instr.getArg2())) arg2 = Integer.parseInt(instr.getArg2());
        else if(Registers.isRegister(instr.getArg2())){
            if(!regs.exists(instr.getArg2().charAt(0))) 
                throw new UndefinedRegisterException("Register "+instr.getArg2() + " is undefined at: "+instr.getRawLine());
            
            arg2 = regs.getValue(instr.getArg2().charAt(0));
        } else throw new InterpreterException("Unknow arg2 format for: "+instr.getRawLine());
        
        regs.attribute(arg1, regs.getValue(arg1) - arg2);
    }

    public void executeMul(Registers regs, Instruction instr) throws InterpreterException{

    }

    public void executeDiv(Registers regs, Instruction instr) throws InterpreterException{

    }


    public void execute(OrderedLL<Instruction> instructions){
            Registers regs = new Registers(); // Inicializa os registradores
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
                switch(instr.getOpcode().toUpperCase()){
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
