package com.aulkhami.pakupos.exceptions;

public class DatabaseException extends AppException {

    public DatabaseException(String message) {
        super(message, "DB_ERROR");
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, "DB_ERROR", cause);
    }
}
