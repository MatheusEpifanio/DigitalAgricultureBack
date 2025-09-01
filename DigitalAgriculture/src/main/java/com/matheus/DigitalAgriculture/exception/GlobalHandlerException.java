package com.matheus.DigitalAgriculture.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(AlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerAlreadyRegistredException(AlreadyRegisteredException alreadyRegisteredException) {
        return alreadyRegisteredException.getMessage();
    }

    @ExceptionHandler(RegisterNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerRegisterNotFound(RegisterNotFound registerNotFound) {
        return registerNotFound.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerException(Exception exception) {
        return "Ocorreu um erro!";
    }
}
