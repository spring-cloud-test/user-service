package com.example.userservice.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException() {
    }

    public UserNameNotFoundException(String message) {
        super(message);
    }
}
