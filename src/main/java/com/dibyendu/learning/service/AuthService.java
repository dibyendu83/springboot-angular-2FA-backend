package com.dibyendu.learning.service;

import com.dibyendu.learning.entity.User;
import com.dibyendu.learning.model.LoginResponse;
import com.dibyendu.learning.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider tokenProvider;


    public LoginResponse loginUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        LoginResponse loginResponse = new LoginResponse();
        // generate Jwt token
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        String jwtToken = tokenProvider.generateToken(claims, email);

        // Set the authentication in SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        loginResponse.setId(user.getId());
        loginResponse.setAuthenticated(authentication.isAuthenticated());
        loginResponse.setAccessToken(jwtToken);
        loginResponse.setEmail(user.getEmail());
        loginResponse.setFirstname(user.getFirstname());
        loginResponse.setLastname(user.getLastname());
        loginResponse.setAddress(user.getAddress());
        loginResponse.setSex(user.getSex());
        loginResponse.setPhoneNo(user.getPhoneNo());
        loginResponse.setLastLogIn(LocalDateTime.now());
        loginResponse.setDob(user.getDob());
        loginResponse.setName(String.join(" ", user.getFirstname(), user.getLastname()));
        if (user.getUser2FA() != null && user.getUser2FA().isEnabled()) {
            loginResponse.setTwoFactorAuthEnabled(Boolean.TRUE);
            loginResponse.setQrCodeUrl(user.getUser2FA().getQrCodeUrl());
        } else {
            loginResponse.setTwoFactorAuthEnabled(Boolean.FALSE);
            loginResponse.setQrCodeUrl("");
        }
        return loginResponse;
    }
}
