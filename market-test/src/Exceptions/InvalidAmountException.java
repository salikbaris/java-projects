package Exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super();
    }

    public InvalidAmountException(String s) {
        super(s);
    }
}
