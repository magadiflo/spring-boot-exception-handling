package dev.magadiflo.app.exceptions.domain;

public class FieldAlreadyExistException extends ConflictException {
    public FieldAlreadyExistException(String message) {
        super(message);
    }
}
