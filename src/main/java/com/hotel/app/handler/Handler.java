package com.hotel.app.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<String>> handleNotValidException(MethodArgumentNotValidException ex) {
        String errMessage = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, errMessage));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public Mono<ResponseEntity<String>> handleNoElementException(NoSuchElementException ex) {
        String errMessage = ex.getMessage();
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, errMessage));
    }
}
