package com.dibyendu.learning.model;

import lombok.Data;

@Data
public class SignUpRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String sex;
    private String phoneNo;
    private String address;
    private String dob;
}
