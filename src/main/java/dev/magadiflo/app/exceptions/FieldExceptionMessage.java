package dev.magadiflo.app.exceptions;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class FieldExceptionMessage {

    private final String field;
    private final List<String> errors = new ArrayList<>();

    public FieldExceptionMessage(String field) {
        this.field = field;
    }

}
