package com.dibyendu.learning.service;

import com.dibyendu.learning.entity.User;
import com.dibyendu.learning.entity.User2FA;
import com.dibyendu.learning.repository.User2FARepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class User2FAService {

    private UserService userService;
    private User2FARepository user2FARepository;

    public User2FA enable2FAuthentication(String username, String secretKey, String qrcodeUrl) {

        User user = userService.loadUserByUsername(username);
        // If user already disable 2FA authentication delete the entry
        if(user.getUser2FA() != null){
            user2FARepository.delete(user.getUser2FA());
        }

        // Store the secret key in the database
        User2FA user2FA = new User2FA();
        user2FA.setUser(user);
        user2FA.setSecretKey(secretKey);
        user2FA.setQrCodeUrl(qrcodeUrl);
        user2FA.setEnabled(true);
        user2FA.setCreatedDate(LocalDateTime.now());
        return user2FARepository.save(user2FA);  // Save to the database
    }

    public User2FA get2FAUserDetails(String email) {
        User user = userService.loadUserByUsername(email);
        return user2FARepository.findByUserId(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not Found."));
    }

    public User2FA disable2FAuthentication(String email) {
        User user = userService.loadUserByUsername(email);
        User2FA user2FA = user2FARepository.findByUserId(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not Found."));
        user2FA.setEnabled(Boolean.FALSE);
        user2FA.setUpdatedDate(LocalDateTime.now());

        User2FA updated2FADetails = user2FARepository.save(user2FA);
        return updated2FADetails;
    }


}
