package dev.magadiflo.app.exceptions.domain;

public class FieldInvalidException extends BadRequestException {
    public FieldInvalidException(String message) {
        super(message);
    }
}
