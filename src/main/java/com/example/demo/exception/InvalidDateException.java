package com.example.demo.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Invalid date format or range.");
    }

    public InvalidDateException(String message) {
        super(message);
    }
}