package com.example.persondata.exception;

public class HouseNotFoundException extends PersonDataException {
    public HouseNotFoundException(String message) {
        super(message);
    }

    public HouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
