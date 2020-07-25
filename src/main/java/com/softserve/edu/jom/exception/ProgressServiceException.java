package com.softserve.edu.jom.exception;

public class ProgressServiceException extends Exception {

    public ProgressServiceException() {
    }

    public ProgressServiceException(String message) {
        super(message);
    }

    public ProgressServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
