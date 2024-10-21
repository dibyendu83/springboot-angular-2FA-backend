package com.dibyendu.learning.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoginResponse {

    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String sex;
    private String phoneNo;
    private String address;
    private String accessToken;
    private boolean isAuthenticated;
    private LocalDateTime lastLogIn;
    private String name;
    private LocalDate dob;
    private boolean twoFactorAuthEnabled;
    private String qrCodeUrl;
}
