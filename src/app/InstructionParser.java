package app;

import exceptions.ParseException;

public class InstructionParser {
    public static Instruction parse(String rawLine) throws ParseException {
        // Acho melhor simplesmente ignorar as linhas vazias ao se executar o código
//        if (rawLine == null || rawLine.trim().isEmpty()) {
//            throw new ParseException("Linha de código não pode ser nula ou vazia.");
//        }

        String[] parts = rawLine.trim().replaceAll("\\s+", " ").split(" ");

        int lineNumber;
        try {
            lineNumber = Integer.parseInt(parts[0]);

            if (lineNumber <= 0) {
                throw new ParseException("Line number must be positive: " + parts[0]);
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Malformed line! Must start with a number: " + rawLine);
        }

        // Indicador da operação: mov, mul, add, jnz, etc
        String opcode = (parts.length > 1) ? parts[1] : null;

        String arg1 = (parts.length > 2) ? parts[2] : null;
        String arg2 = (parts.length > 3) ? parts[3] : null;
        String arg3 = (parts.length > 4) ? parts[4] : null;

        return new Instruction(rawLine.trim(), lineNumber, opcode, arg1, arg2, arg3);
    }
}
