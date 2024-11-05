package com.lirmo.uber.uberApp.exceptions;

public class DriverNotAvailableException extends RuntimeException{
    public DriverNotAvailableException() {
    }

    public DriverNotAvailableException(String message) {
        super(message);
    }
}
