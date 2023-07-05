package com.example.controller.advice;

import com.example.errors.BusinessException;
import com.example.errors.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class IssueControllerAdvice {

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handle(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseError(HttpStatus.BAD_REQUEST.name(), Integer.toString(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handle(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseError(HttpStatus.NOT_FOUND.name(), Integer.toString(HttpStatus.NOT_FOUND.value()), ex.getMessage());
    }

}
