package me.aborozdykh.exception;

/**
 * @author Andrii Borozdykh
 */
public class WrongKeyException extends RuntimeException {
    public WrongKeyException(String message) {
        super(message);
    }
}
