package com.aulkhami.mavenproject1.exceptions;

public class AuthenticationException extends AppException {

    public AuthenticationException(String message) {
        super(message, "AUTH_ERROR");
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, "AUTH_ERROR", cause);
    }
}
