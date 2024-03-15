package dev.magadiflo.app.exceptions.domain;

public class MalformedHeaderException extends BadRequestException {
    public MalformedHeaderException(String message) {
        super(message);
    }
}
