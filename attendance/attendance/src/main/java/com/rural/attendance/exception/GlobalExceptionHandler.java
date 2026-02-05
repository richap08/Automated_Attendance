package com.rural.attendance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleDuplicateKey(SQLIntegrityConstraintViolationException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Roll number already exists");
    }
}
