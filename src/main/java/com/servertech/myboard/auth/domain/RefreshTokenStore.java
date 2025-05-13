package com.servertech.myboard.auth.domain;

import java.util.Optional;

public interface RefreshTokenStore {
	void save(RefreshToken refreshToken);
	Optional<String> find(String email);
	void delete(String email);
}
