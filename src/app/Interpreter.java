package app;

import datastructures.Node;
import datastructures.OrderedLL;
import exceptions.InterpreterException;
import exceptions.UndefinedRegisterException;

public class Interpreter{
    private int validateArg2(Registers regs, Instruction instr) throws InterpreterException {
        if(instr.getArg2() == null)
            throw new InterpreterException("Incorrect number of arguments at: "+instr.getRawLine());

        int arg2;
        if(Registers.isRegister(instr.getArg2())){
            if(!regs.exists(instr.getArg2().charAt(0)))
                throw new InterpreterException("Register "+instr.getArg2() + " is undefined!: "+instr.getRawLine());
            arg2 = regs.getValue(instr.getArg2().charAt(0));
        } else if(Util.isNumber(instr.getArg2())){
            arg2 = Integer.parseInt(instr.getArg2());
        } else throw new InterpreterException("Unknow arg2 format for: "+instr.getRawLine());

        return arg2;
    }

    public void execute(OrderedLL<Instruction> instructions) throws InterpreterException {
        Registers regs = new Registers(); // Inicializa os registradores
        Node<Instruction> current = instructions.getHead(); // É uma má prática expor os Nodes assim??

        for(int i=0; i<instructions.getSize(); i++){
            Instruction instr = instructions.get(i);
            String opcode = instr.getOpcode().toUpperCase();
            if(instr.getArg1() == null || instr.getTrash() != null)
                throw new InterpreterException("Incorrect number of arguments!: "+instr.getRawLine());

            if(!(Registers.isRegister(instr.getArg1())))
                throw new InterpreterException("Arg 1 must be a register: " + instr.getRawLine());

            if(!opcode.equals("MOV") && !regs.exists(instr.getArg1().charAt(0)))
                throw new UndefinedRegisterException("Register "+instr.getArg1() + " is undefined!");

            try{

                switch(instr.getOpcode().toUpperCase()){
                    case "MOVE":
                        executeMov(regs, instr);
                        break;
                    case "INC":
                        executeInc(regs, instr);
                        break;
                    case "DEC":
                        executeDec(regs, instr);
                        break;
                    case "ADD":
                        executeAdd(regs, instr);
                        break;
                    case "SUB":
                        executeSub(regs, instr);
                        break;
                    case "MUL":
                        executeMul(regs, instr);
                        break;
                    case "DIV":
                        executeDiv(regs, instr);
                        break;
                    case "OUT":
                        executeOut(regs, instr);
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
                        // Proposta do Theozão: procura o índice da linha Y. Depois, atribui i = arg2;
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
        }

//        boolean jumped = false; // Flag para controlar se um salto ocorreu

    }

    public void executeMov(Registers regs, Instruction instr) throws InterpreterException {
        char arg1 = instr.getArg1().charAt(0);
        int arg2 = validateArg2(regs, instr);
        regs.attribute(arg1, arg2);
    }

    public void executeInc(Registers regs, Instruction instr) throws InterpreterException{
        if(instr.getArg2() != null)
            throw new InterpreterException("Incorrect number of arguments for INC!: "+instr.getRawLine());

        char arg1 = instr.getArg1().charAt(0);
        int newValueForReg = regs.getValue(arg1) + 1;
        regs.attribute(arg1, newValueForReg);
    }

    public void executeDec(Registers regs, Instruction instr) throws InterpreterException{
        if(instr.getArg2() != null)
            throw new InterpreterException("Incorrect number of arguments for DEC!: "+instr.getRawLine());

        char arg1 = instr.getArg1().charAt(0);

        regs.attribute(arg1, regs.getValue(arg1) - 1);
    }

    public void executeAdd(Registers regs, Instruction instr) throws InterpreterException{
        char arg1 = instr.getArg1().charAt(0);
        int arg2 = validateArg2(regs, instr);

        int sum = regs.getValue(arg1) + arg2;
        regs.attribute(arg1, sum);
    }

    public void executeSub(Registers regs, Instruction instr) throws InterpreterException {
        char arg1 = instr.getArg1().charAt(0);
        int arg2 = validateArg2(regs, instr);

        regs.attribute(arg1, regs.getValue(arg1) - arg2);
    }

    public void executeMul(Registers regs, Instruction instr) throws InterpreterException{
        char arg1 = instr.getArg1().charAt(0);
        int arg2 = validateArg2(regs, instr);

        regs.attribute(arg1, regs.getValue(arg1) * arg2);
    }

    public void executeDiv(Registers regs, Instruction instr) throws InterpreterException{
        char arg1 = instr.getArg1().charAt(0);
        int arg2 = validateArg2(regs, instr);
        if(arg2 == 0) throw new ArithmeticException("Division per 0 at: "+instr.getRawLine());
        regs.attribute(arg1, regs.getValue(arg1) / arg2);
    }

    public void executeOut(Registers regs, Instruction instr) throws InterpreterException {
        if(instr.getArg2() != null)
            throw new InterpreterException("Incorrect number of arguments for OUT!: "+instr.getRawLine());
        System.out.println(regs.getValue(instr.getArg1().charAt(0)));
    }
}
