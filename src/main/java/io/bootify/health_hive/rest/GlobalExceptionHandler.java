package io.bootify.health_hive.rest;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsatisfiedDependencyException.class)
    public ResponseEntity<String> handleUnsatisfiedDependencyException(UnsatisfiedDependencyException ex) {
        return new ResponseEntity<>("Error creating bean: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}