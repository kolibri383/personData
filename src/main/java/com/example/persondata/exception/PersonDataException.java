package com.example.persondata.exception;

public class PersonDataException extends RuntimeException{
    public PersonDataException(String message) {
        super(message);
    }

    public PersonDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
