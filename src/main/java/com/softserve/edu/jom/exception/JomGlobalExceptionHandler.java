package com.softserve.edu.jom.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "EntityNotFoundException occurred")
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException() {
        log.warn("Entity not found exception raised.");
    }

//    @ExceptionHandler(UserServiceException.class)
//    public ModelAndView handleAllExceptionMethod(Exception ex, HttpServletRequest request) {
//        StringBuilder sb = new StringBuilder();
//
//        String exceptionMessage = ex.getLocalizedMessage();
//        if (ex.getCause() instanceof ConstraintViolationException) {
//            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex.getCause()).getConstraintViolations();
//            for (ConstraintViolation constraintViolation : constraintViolations) {
//                sb.append(constraintViolation.getMessageTemplate());
//                sb.append(";");
//            }
//            exceptionMessage = sb.toString();
//        }
//
////        if (ex.getCause() instanceof DataIntegrityViolationException) {
////            Set<ConstraintViolation<?>> constraintViolations = ((DataIntegrityViolationException) ex.getCause()).getConstraintViolations();
////            for (ConstraintViolation constraintViolation : constraintViolations) {
////                sb.append(constraintViolation.getMessageTemplate());
////                sb.append(";");
////            }
////            exceptionMessage = sb.toString();
////        }
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", ex);
//        modelAndView.addObject("path", request.getRequestURL());
//        modelAndView.addObject("path", request.getRequestURL());
//        modelAndView.addObject("message", exceptionMessage);
//        modelAndView.setViewName("error");
//
//        return modelAndView;
}
