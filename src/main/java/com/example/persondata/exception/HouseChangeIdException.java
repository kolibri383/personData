package com.example.persondata.exception;

public class HouseChangeIdException extends PersonDataException {
    public HouseChangeIdException(String message) {
        super(message);
    }

    public HouseChangeIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
