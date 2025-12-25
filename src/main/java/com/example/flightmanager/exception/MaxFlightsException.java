package com.example.flightmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaxFlightsException extends RuntimeException {
    public MaxFlightsException(String message) {
        super(message);
    }
}