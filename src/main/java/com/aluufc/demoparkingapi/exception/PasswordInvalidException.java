package com.aluufc.demoparkingapi.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String s) {
        super(s);
    }
}
