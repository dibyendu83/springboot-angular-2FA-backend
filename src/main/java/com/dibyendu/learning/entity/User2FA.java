package com.dibyendu.learning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_2fa_authentication")
@Data
public class User2FA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)// Foreign key to the User table
    @ToString.Exclude
    private User user;  // Reference to the User entity

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name="qrcode_url")
    private String qrCodeUrl;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

}
