package dev.magadiflo.app.exceptions;

import dev.magadiflo.app.exceptions.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionMessage> notFoundException(Exception exception) {
        return this.responseEntity(exception.getClass().getSimpleName(), HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({
            BadRequestException.class,
            MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingRequestHeaderException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiExceptionMessage> badRequestException(Exception exception) {

        if (exception instanceof MethodArgumentNotValidException) {
            List<FieldExceptionMessage> errors = new ArrayList<>();
            Map<String, FieldExceptionMessage> validatedFieldMap = new HashMap<>();
            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();

            bindingResult.getAllErrors().forEach(objectError -> {
                String fieldName = ((FieldError) objectError).getField();
                String errorMessage = objectError.getDefaultMessage();

                FieldExceptionMessage fieldExceptionMessage = validatedFieldMap.computeIfAbsent(fieldName, FieldExceptionMessage::new);
                fieldExceptionMessage.getErrors().add(errorMessage);

                if (!errors.contains(fieldExceptionMessage)) {
                    errors.add(fieldExceptionMessage);
                }
            });

            return this.responseEntity(exception.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "Se detectaron errores en los campos", errors);
        }

        return this.responseEntity(exception.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({
            ConflictException.class,
            DataIntegrityViolationException.class,
            DuplicateKeyException.class
    })
    public ResponseEntity<ApiExceptionMessage> conflictException(Exception exception) {
        String message = switch (exception) {
            case DuplicateKeyException duplicate -> duplicate.getMostSpecificCause().toString();
            case DataIntegrityViolationException di -> di.getMostSpecificCause().toString().split(":")[1].trim();
            default -> exception.getMessage();
        };
        return this.responseEntity(exception.getClass().getSimpleName(), HttpStatus.CONFLICT, message);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiExceptionMessage> forbiddenException(Exception exception) {
        return this.responseEntity(exception.getClass().getSimpleName(), HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Void> unauthorizedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionMessage> fatalErrorUnexpectedException(Exception exception) {
        return this.responseEntity(exception.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ApiExceptionMessage> responseEntity(String simpleName, HttpStatus httpStatus, String message) {
        return this.responseEntity(simpleName, httpStatus, message, null);
    }

    private ResponseEntity<ApiExceptionMessage> responseEntity(String simpleName, HttpStatus httpStatus, String message, List<FieldExceptionMessage> fieldErrors) {
        return ResponseEntity.status(httpStatus).body(new ApiExceptionMessage(simpleName, httpStatus, message, fieldErrors));
    }
}
