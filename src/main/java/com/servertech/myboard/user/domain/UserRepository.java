package com.servertech.myboard.user.domain;

public interface UserRepository {
	void save(User user);
	User findByEmail(String email);
}
