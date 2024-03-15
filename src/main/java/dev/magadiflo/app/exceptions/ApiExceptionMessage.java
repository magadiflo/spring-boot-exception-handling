package dev.magadiflo.app.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@ToString
@Getter
public class ApiExceptionMessage {

    private final String simpleName;
    private final String httpStatus;
    private final Integer code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<FieldExceptionMessage> fieldErrors;

    public ApiExceptionMessage(String simpleName, HttpStatus httpStatus, String message, List<FieldExceptionMessage> fieldErrors) {
        this.simpleName = simpleName;
        this.httpStatus = httpStatus.name();
        this.code = httpStatus.value();
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

}
