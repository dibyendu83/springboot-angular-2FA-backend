package com.dibyendu.learning.model;

import lombok.Data;

@Data
public class TwoFactorAuthRequest {

    String otpCode;
    String username;
}
