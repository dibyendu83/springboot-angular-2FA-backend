package com.dibyendu.learning.repository;

import com.dibyendu.learning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String emailId);

}
