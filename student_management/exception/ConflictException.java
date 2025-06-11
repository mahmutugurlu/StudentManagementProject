package com.tpe.student_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) //403 Conflict
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
