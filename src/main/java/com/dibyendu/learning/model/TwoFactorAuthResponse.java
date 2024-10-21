package com.dibyendu.learning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorAuthResponse {

    private long id;
    private String email;
    private boolean enabled;
    private String qrCodeUrl;
}
