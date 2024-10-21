package com.dibyendu.learning.security;

import com.dibyendu.learning.entity.User;
import com.dibyendu.learning.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Load user by email
        User user;
        try {
            user = userDetailsService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            // If user is not found, throw a custom exception
            throw new UsernameNotFoundException("User with the provided email does not exist.");
        }

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // If password is incorrect, throw a custom exception
            throw new BadCredentialsException("Incorrect password. Please try again.");
        }

        // If authentication is successful, return the authentication token
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
