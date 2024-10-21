package com.dibyendu.learning.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid email or password";

        // Customize the message based on the exception type
        if (exception.getMessage().contains("User not found")) {
            errorMessage = "User with the provided email does not exist.";
        } else if (exception.getMessage().contains("Bad credentials")) {
            errorMessage = "Incorrect email or password. Please try again.";
        }

        // Set HTTP status and response
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
        response.getWriter().flush();
    }
}
