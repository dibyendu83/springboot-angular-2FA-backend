package com.dibyendu.learning.exception;

public class TwoFactorAuthenticationException extends RuntimeException {
    public TwoFactorAuthenticationException(String message) {
        super(message);
    }
}
