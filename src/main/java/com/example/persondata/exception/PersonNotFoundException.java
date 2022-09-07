package com.example.persondata.exception;

public class PersonNotFoundException extends PersonDataException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
