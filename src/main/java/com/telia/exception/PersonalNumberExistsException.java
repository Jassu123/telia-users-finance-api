package com.telia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonalNumberExistsException extends RuntimeException{
    private String message;

    public PersonalNumberExistsException(String message){
        super(message);
    }
}