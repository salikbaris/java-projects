// Başak Barış Salık 2017203072
// Ata Gündüz Uyan 2021502135

package Exceptions;

public class InvalidIdException extends RuntimeException{
    public InvalidIdException() {
        super();
    }
    public InvalidIdException(String s) {
        super(s);
    }
}
