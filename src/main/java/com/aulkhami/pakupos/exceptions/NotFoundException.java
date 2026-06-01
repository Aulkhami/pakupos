package com.aulkhami.pakupos.exceptions;

public class NotFoundException extends AppException {

    public NotFoundException(String message) {
        super(message, "NOT_FOUND");
    }
}
