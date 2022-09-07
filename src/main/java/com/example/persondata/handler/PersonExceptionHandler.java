package com.example.persondata.handler;

import com.example.persondata.exception.*;
import org.hibernate.PersistentObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class PersonExceptionHandler {

    @ExceptionHandler({PersonNotFoundException.class,
            HouseNotFoundException.class,
            CarNotFoundException.class})
    public <T extends PersonDataException> ResponseEntity<Object> handlerNotFoundException(T ex, ServletWebRequest request) {
        var path = request.getRequest().getRequestURI();
        var error = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now(), path);
        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PersonChangeIdException.class,
            CarChangeIdException.class,
            HouseChangeIdException.class})
    public <T extends PersonDataException> ResponseEntity<Object> handlerChangeIdException(T ex, ServletWebRequest request) {
        var path = request.getRequest().getRequestURI();
        var error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), path);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonHouseAddressModificationException.class)
    public ResponseEntity<Object> handlerPersonHouseAddressModificationException(PersonHouseAddressModificationException ex,
                                                                                 ServletWebRequest request) {
        var path = request.getRequest().getRequestURI();
        var error = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), path);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HouseSaveException.class)
    public ResponseEntity<Object> handlerHouseSaveException(HouseSaveException ex,
                                                            ServletWebRequest request) {
        var path = request.getRequest().getRequestURI();
        var error = new ApiError(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now(), path);
        return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerValidException(MethodArgumentNotValidException ex, ServletWebRequest request) {
        var path = request.getRequest().getRequestURI();
        var message = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        var error = new ApiError(message, HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now(), path);
        return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(PersistentObjectException.class)
    public ResponseEntity<Object> handlerPersistObjectException(PersistentObjectException ex, ServletWebRequest request) {
        var path = request.getRequest().getRequestURI();
        var message = "unable to process the object " + ex.getMessage().split("persondata.entity.")[1];
        var error = new ApiError(message, HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now(), path);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
