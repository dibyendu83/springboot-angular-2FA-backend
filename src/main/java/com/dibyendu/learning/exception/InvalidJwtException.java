package com.dibyendu.learning.exception;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message) {
        super(message);
    }
    public InvalidJwtException() {
        super();
    }
}
