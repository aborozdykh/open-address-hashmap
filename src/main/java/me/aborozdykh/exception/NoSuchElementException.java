package me.aborozdykh.exception;

/**
 * @author Andrii Borozdykh
 */
public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(String message) {
        super(message);
    }
}
