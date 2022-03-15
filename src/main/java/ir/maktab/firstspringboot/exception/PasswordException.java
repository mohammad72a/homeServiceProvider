package ir.maktab.firstspringboot.exception;

public class PasswordException extends RuntimeException{
    public PasswordException() {
    }

    public PasswordException(String message) {
        super(message);
    }
}
