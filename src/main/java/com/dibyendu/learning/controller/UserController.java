package com.dibyendu.learning.controller;

import com.dibyendu.learning.exception.ProfileUpdateException;
import com.dibyendu.learning.model.LoginResponse;
import com.dibyendu.learning.model.SignUpRequest;
import com.dibyendu.learning.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/user/profile")
    public ResponseEntity<LoginResponse> updateUserProfile(@RequestBody SignUpRequest signUpRequest) {
        try {
            LoginResponse response = userService.updateUserDetails(signUpRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ProfileUpdateException("Failed to update profile. Please try again.");
        }
    }
}
