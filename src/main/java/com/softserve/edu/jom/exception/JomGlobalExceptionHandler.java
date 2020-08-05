package com.softserve.edu.jom.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class JomGlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public ModelAndView handleValidationException(BindException bindEx) {
        ModelAndView modelAndView = new ModelAndView("error/400");
        String errMsg = bindEx.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("; "));
        modelAndView.addObject("info", errMsg);
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleValidationException(Exception ex) {
//        ModelAndView modelAndView = new ModelAndView("error/error");
//        modelAndView.addObject("info", ex.getMessage());
//        modelAndView.setStatus(HttpStatus.I_AM_A_TEAPOT);
//        return modelAndView;
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("info", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
