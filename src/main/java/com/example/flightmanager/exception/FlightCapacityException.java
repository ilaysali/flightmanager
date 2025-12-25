package com.example.flightmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FlightCapacityException extends RuntimeException {
    public FlightCapacityException(String message) {
        super(message);
    }
}