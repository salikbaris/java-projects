package Exceptions;

public class InvalidIdException extends RuntimeException{
    public InvalidIdException() {
        super();
    }
    public InvalidIdException(String s) {
        super(s);
    }
}
