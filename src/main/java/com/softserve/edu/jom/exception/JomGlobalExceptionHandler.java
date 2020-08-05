package com.softserve.edu.jom.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class JomGlobalExceptionHandler {
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "EntityNotFoundException occurred")
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException() {
        log.warn("Entity not found exception raised.");
    }
}
