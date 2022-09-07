package com.example.persondata.exception;

public class CarNotFoundException extends PersonDataException {
    public CarNotFoundException(String message) {
        super(message);
    }

    public CarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
