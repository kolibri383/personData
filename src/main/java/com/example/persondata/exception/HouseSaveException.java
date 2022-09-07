package com.example.persondata.exception;

public class HouseSaveException extends PersonDataException {
    public HouseSaveException(String message) {
        super(message);
    }

    public HouseSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
