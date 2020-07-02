package com.prodyna.pac.pacbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LocationExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(LocationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String locationExceptionHandler(LocationException le) {
        return le.getMessage();
    }

}
