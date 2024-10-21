package com.dibyendu.learning.controller;

import com.dibyendu.learning.exception.ProfileUpdateException;
import com.dibyendu.learning.exception.TwoFactorAuthenticationException;
import com.dibyendu.learning.model.LoginResponse;
import com.dibyendu.learning.model.TwoFactorAuthRequest;
import com.dibyendu.learning.model.TwoFactorAuthResponse;
import com.dibyendu.learning.service.TwoFactorAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/2fa")
@AllArgsConstructor
public class TwoFactorAuthController {

    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    @PostMapping("/setup")
    public ResponseEntity<TwoFactorAuthResponse> setupTwoFactorAuthentication(@RequestBody String username) {
        try {
            TwoFactorAuthResponse response = twoFactorAuthenticationService.enableTwoFactorAuthentication(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            throw new TwoFactorAuthenticationException("Failed to generate QR Code. Please try again.");
        }
    }

    @PostMapping("/disable")
    public ResponseEntity<TwoFactorAuthResponse> disableTwoFactorAuthentication(@RequestBody String username) {
        try {
            TwoFactorAuthResponse response = twoFactorAuthenticationService.disableTwoFactorAuthentication(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            throw new TwoFactorAuthenticationException("Failed to disable 2FA. Please try again.");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateOtp(@RequestBody TwoFactorAuthRequest twoFactorAuthRequest) {

        if(twoFactorAuthRequest.getOtpCode().isBlank())
            throw new TwoFactorAuthenticationException("Please enter the OTP to proceed.");

        try {
            boolean result = twoFactorAuthenticationService.validateOtp(Integer.parseInt(twoFactorAuthRequest.getOtpCode()),twoFactorAuthRequest.getUsername());

            return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
        } catch (Exception ex) {
            throw new TwoFactorAuthenticationException("Failed to validate the OTP. Please try again.");
        }
    }
}
