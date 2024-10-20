package com.allocation.manager.exceptions;

public class InsufficientWorkHoursException extends RuntimeException {
    public InsufficientWorkHoursException(String message) {
        super(message);
    }
}
