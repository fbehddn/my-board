package com.servertech.myboard.user.domain;

import java.util.Optional;

public interface UserRepository {
	void save(User user);
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long id);
	User getReferenceById(Long userId);
}
