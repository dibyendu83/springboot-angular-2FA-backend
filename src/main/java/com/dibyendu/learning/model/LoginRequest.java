package com.dibyendu.learning.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
