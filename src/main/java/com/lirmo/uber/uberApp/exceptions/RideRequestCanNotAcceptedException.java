package com.lirmo.uber.uberApp.exceptions;

public class RideRequestCanNotAcceptedException extends RuntimeException {
    public RideRequestCanNotAcceptedException() {
    }

    public RideRequestCanNotAcceptedException(String message) {
        super(message);
    }
}
