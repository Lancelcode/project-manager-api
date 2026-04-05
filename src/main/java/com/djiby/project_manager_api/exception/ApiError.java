package com.djiby.project_manager_api.exception;

import java.util.List;

public class ApiError {

    private String message;
    private List<String> errors;

    public ApiError(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}