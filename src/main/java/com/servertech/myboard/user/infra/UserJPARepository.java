package com.servertech.myboard.user.infra;

import com.servertech.myboard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
