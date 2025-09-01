package com.matheus.DigitalAgriculture.exception;

public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(String menssage) {
        super(menssage);
    }
}
