package ir.maktab.firstspringboot.exception;

public class ReviewException extends RuntimeException{
    public ReviewException() {
    }

    public ReviewException(String message) {
        super(message);
    }
}
