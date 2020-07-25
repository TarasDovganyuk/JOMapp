package com.softserve.edu.jom.exception;

public class MarathonServiceException extends RuntimeException {

    public MarathonServiceException(String message) {
        super(message);
    }

    public MarathonServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
