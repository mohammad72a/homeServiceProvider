package ir.maktab.firstspringboot.exception;

public class CreditException extends RuntimeException{
    public CreditException() {
    }

    public CreditException(String message) {
        super(message);
    }
}
