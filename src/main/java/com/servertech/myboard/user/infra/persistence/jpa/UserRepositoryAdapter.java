package com.servertech.myboard.user.infra.persistence.jpa;

import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
	private final UserJpaRepository userJpaRepository;

	@Override
	public void save(User user) {
		userJpaRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userJpaRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userJpaRepository.findById(id);
	}

	@Override
	public User getReferenceById(Long userId) {
		return userJpaRepository.getReferenceById(userId);
	}
}
