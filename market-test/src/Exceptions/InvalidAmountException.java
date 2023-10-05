// Başak Barış Salık 2017203072
// Ata Gündüz Uyan 2021502135

package Exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super();
    }

    public InvalidAmountException(String s) {
        super(s);
    }
}
