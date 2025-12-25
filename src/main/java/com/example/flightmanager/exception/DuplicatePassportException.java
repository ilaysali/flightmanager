package com.example.flightmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePassportException extends RuntimeException {
    public DuplicatePassportException(String passportId) {
        super("Passenger with passport ID " + passportId + " already exists.");
    }
}