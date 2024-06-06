package org.example.parcial.handlers;

import lombok.extern.slf4j.Slf4j;
import org.example.parcial.domain.dtos.GeneralResponse;
import org.example.parcial.utils.ErrorsTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
// Define un componente global que proporciona manejo centralizado de excepciones y vinculacion de datos
@Slf4j
public class GlobalErrorHandler {
    private final ErrorsTool errorsTool;

    public GlobalErrorHandler(ErrorsTool errorsTool) {
        this.errorsTool = errorsTool;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse> GeneralHandler(Exception ex) {
        return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GeneralResponse> ResourceNotFoundHandler(NoResourceFoundException ex) {
        return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> BadRequestHandler(MethodArgumentNotValidException ex) {
        return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, errorsTool.mapErrors(ex.getBindingResult().getFieldErrors()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralResponse> IllegalArgumentHandler(IllegalArgumentException ex) {
        return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GeneralResponse> IllegalStateHandler(IllegalStateException ex) {
        return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<GeneralResponse> forbiddenException(HttpClientErrorException.Forbidden ex) {
        return GeneralResponse.getResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<GeneralResponse> unauthorizedException(HttpClientErrorException.Unauthorized ex) {
        return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(InternalError.class)
    public ResponseEntity<GeneralResponse> internalErrorHandler(InternalError ex) {
        return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<GeneralResponse> dateTimeExceptionHandler(DateTimeException ex) {
        return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}