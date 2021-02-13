package com.georgidinov.roiti.schoolgrading.controlleradvise;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleItemInvalidFormatException(InvalidFormatException exception) {
        return new ErrorMessage(logError(exception));
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNumberFormatException(NumberFormatException exception) {
        return new ErrorMessage(logError(exception));
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchElementException(NoSuchElementException exception) {
        return new ErrorMessage(logError(exception));
    }

    @ExceptionHandler(EntityNotFoundCustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotFoundCustomException(EntityNotFoundCustomException exception) {
        return new ErrorMessage(logError(exception));
    }

    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleEntityValidationException(EntityValidationException exception) {
        return new ErrorMessage(logError(exception));
    }


    //== private methods ==
    private String logError(Exception exception) {
        String exceptionMessage = exception.getMessage();
        log.error("Handling {} with message = {}", exception.getClass().getSimpleName(), exceptionMessage);
        return exceptionMessage;
    }

}