package com.dibyendu.learning.repository;

import com.dibyendu.learning.entity.User2FA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User2FARepository extends JpaRepository<User2FA, Long> {

    Optional<User2FA> findByUserId(long id);

}
