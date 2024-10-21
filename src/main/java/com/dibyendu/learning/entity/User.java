package com.dibyendu.learning.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 8)
    private String sex;

    @Column(name = "phoneno")
    private String phoneNo;

    @Column(length = 1000)
    private String address;

    private LocalDate dob;

    @OneToOne(mappedBy = "user") // Establishing the 1-to-1 mapping with User2FA
    @ToString.Exclude
    private User2FA user2FA;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = List.of("ROLE_USER");
        return roles.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
