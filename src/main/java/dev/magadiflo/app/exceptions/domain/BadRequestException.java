package dev.magadiflo.app.exceptions.domain;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
