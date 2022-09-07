package com.example.persondata.exception;

public class PersonChangeIdException extends PersonDataException{
    public PersonChangeIdException(String message) {
        super(message);
    }

    public PersonChangeIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
