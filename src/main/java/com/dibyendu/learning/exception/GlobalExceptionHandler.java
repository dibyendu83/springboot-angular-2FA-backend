package com.dibyendu.learning.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle Invalid JWT Token Exception
    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJwt(InvalidJwtException ex) {
        Map<String, String> response = new HashMap<>();
        String message = ex.getMessage();
        if (message == null){
            message = "Invalid JWT token. Please log in again.";
        }
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Handle Generic Exceptions (e.g., Server Errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Something went wrong. Please try again later.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Custom Business Logic Exceptions (if any)
    @ExceptionHandler(ProfileUpdateException.class)
    public ResponseEntity<Map<String, String>> handleProfileUpdateException(ProfileUpdateException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TwoFactorAuthenticationException.class)
    public ResponseEntity<Map<String, String>> handle2FAException(TwoFactorAuthenticationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
