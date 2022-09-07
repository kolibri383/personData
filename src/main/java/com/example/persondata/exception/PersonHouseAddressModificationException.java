package com.example.persondata.exception;

public class PersonHouseAddressModificationException extends PersonDataException{
    public PersonHouseAddressModificationException(String message) {
        super(message);
    }

    public PersonHouseAddressModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
