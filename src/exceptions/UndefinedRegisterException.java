package exceptions;

public class UndefinedRegisterException extends InterpreterException {
    public UndefinedRegisterException(String message) {
        super(message);
    }
}
