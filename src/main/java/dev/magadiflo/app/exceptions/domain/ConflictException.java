package dev.magadiflo.app.exceptions.domain;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
