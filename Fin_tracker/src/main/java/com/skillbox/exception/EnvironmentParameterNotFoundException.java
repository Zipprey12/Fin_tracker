package com.skillbox.exception;

public class EnvironmentParameterNotFoundException extends RuntimeException {
    public EnvironmentParameterNotFoundException(String message) {
        super(message);
    }
}
