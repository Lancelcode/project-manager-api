package com.djiby.project_manager_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiError> handleProjectNotFound(ProjectNotFoundException ex) {
        ApiError error = new ApiError(ex.getMessage(), List.of());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(TaskNotFoundException ex) {
        ApiError error = new ApiError(ex.getMessage(), List.of());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(error.getField() + " " + error.getDefaultMessage())
        );
        ApiError apiError = new ApiError("Validation failed", errors);
        return ResponseEntity.badRequest().body(apiError);
    }
}