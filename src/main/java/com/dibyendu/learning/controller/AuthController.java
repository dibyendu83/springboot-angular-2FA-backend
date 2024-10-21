package com.dibyendu.learning.controller;

import com.dibyendu.learning.model.LoginRequest;
import com.dibyendu.learning.model.LoginResponse;
import com.dibyendu.learning.model.SignUpRequest;
import com.dibyendu.learning.service.AuthService;
import com.dibyendu.learning.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody SignUpRequest signUpRequest) {
        Map<String, String> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        String message;
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            message = "Error: Email is already in use!";
            httpStatus = HttpStatus.CONFLICT;
        } else {
            message = "User registered successfully!";
            // Continue with registration process if email is not taken
            userService.createUser(signUpRequest);
        }
        response.put("message", message);
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException ex) {
            // Handle BadCredentialsException or any authentication-related exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Incorrect email or password. Please try again."));
        }
    }
}
