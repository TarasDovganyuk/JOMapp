package com.softserve.edu.jom.exception;

public class SprintServiceException extends RuntimeException {

    public SprintServiceException(String message) {
        super(message);
    }

    public SprintServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
