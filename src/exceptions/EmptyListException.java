package exceptions;

public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
    }

    public EmptyListException() {}
}
