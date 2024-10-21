package com.dibyendu.learning.service;

import com.dibyendu.learning.entity.User;
import com.dibyendu.learning.model.LoginResponse;
import com.dibyendu.learning.model.SignUpRequest;
import com.dibyendu.learning.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found with email: " + username));
    }

    public boolean existsByEmail(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    public void createUser(SignUpRequest signUpRequest) {
        User user = prepareUserDetails(signUpRequest);
        this.userRepository.save(user);
    }

    private User prepareUserDetails(SignUpRequest signUpRequest) {
        User user = User.builder()
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .sex(signUpRequest.getSex())
                .phoneNo(signUpRequest.getPhoneNo())
                .address(signUpRequest.getAddress())
                .email(signUpRequest.getEmail())
                .dob(LocalDate.parse(signUpRequest.getDob()))
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
        return user;
    }

    public LoginResponse updateUserDetails(SignUpRequest signUpRequest) {
        User existingUser =  this.loadUserByUsername(signUpRequest.getEmail());
        existingUser.setFirstname(signUpRequest.getFirstname());
        existingUser.setLastname(signUpRequest.getLastname());
        existingUser.setAddress(signUpRequest.getAddress());
        existingUser.setSex(signUpRequest.getSex());
        existingUser.setPhoneNo(signUpRequest.getPhoneNo());
        existingUser.setDob(LocalDate.parse(signUpRequest.getDob()));

        User user = this.userRepository.save(existingUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setFirstname(user.getFirstname());
        loginResponse.setLastname(user.getLastname());
        loginResponse.setAddress(user.getAddress());
        loginResponse.setSex(user.getSex());
        loginResponse.setPhoneNo(user.getPhoneNo());
        loginResponse.setDob(user.getDob());
        loginResponse.setName(String.join(" ", user.getFirstname(), user.getLastname()));
        return loginResponse;
    }
}
