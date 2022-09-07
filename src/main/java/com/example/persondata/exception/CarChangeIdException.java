package com.example.persondata.exception;

public class CarChangeIdException extends PersonDataException {
    public CarChangeIdException(String message) {
        super(message);
    }

    public CarChangeIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
