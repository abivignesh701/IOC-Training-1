package com.example.ioc.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

/**
 * Translates domain and input validation exceptions into consistent HTTP error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ApiError(int status, String error, String message, Instant timestamp) {
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(StudentNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateStudentException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiError> handleBadRequest(Exception ex) {
        String message = ex instanceof IllegalArgumentException
                ? ex.getMessage()
                : "Malformed request payload or parameter";
        return buildError(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status, String message) {
        ApiError body = new ApiError(status.value(), status.getReasonPhrase(), message, Instant.now());
        return ResponseEntity.status(status).body(body);
    }
}
