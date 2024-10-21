package com.dibyendu.learning.service;

import com.dibyendu.learning.entity.User2FA;
import com.dibyendu.learning.model.TwoFactorAuthResponse;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TwoFactorAuthenticationService {

    private final IGoogleAuthenticator googleAuthenticator;
    private final User2FAService user2FAService;


    public String generateQRCode(String username, GoogleAuthenticatorKey key) {
        // Generate the QR code URL to send to the front-end
        return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("MyApp", username, key);
    }

    public TwoFactorAuthResponse enableTwoFactorAuthentication(String username) {
        // Generate the secret key
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        String secretKey = key.getKey();

        // Return the QR code URL
        String qrcodeUrl = generateQRCode(username, key);
        User2FA user2FA = user2FAService.enable2FAuthentication(username, secretKey, qrcodeUrl);

        TwoFactorAuthResponse twoFactorAuthResponse = new TwoFactorAuthResponse(
                user2FA.getUser().getId(), user2FA.getUser().getEmail(), user2FA.isEnabled(), qrcodeUrl);

        return twoFactorAuthResponse;
    }

    public TwoFactorAuthResponse disableTwoFactorAuthentication(String username) {

        User2FA user2FA = user2FAService.disable2FAuthentication(username);

        TwoFactorAuthResponse twoFactorAuthResponse = new TwoFactorAuthResponse(
                user2FA.getUser().getId(), user2FA.getUser().getEmail(), user2FA.isEnabled(), "");

        return twoFactorAuthResponse;
    }

    public boolean validateOtp(int otp, String email) {
        User2FA user2FA = user2FAService.get2FAUserDetails(email);
        String secretKey = user2FA.getSecretKey();
        return googleAuthenticator.authorize(secretKey, otp);
    }
}
