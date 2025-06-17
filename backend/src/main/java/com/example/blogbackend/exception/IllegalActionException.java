package com.example.blogbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Or another suitable status like CONFLICT (409)
public class IllegalActionException extends RuntimeException {
    public IllegalActionException(String message) {
        super(message);
    }
}
