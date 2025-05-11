package com.servertech.myboard.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {
	Optional<RefreshToken> findByEmail(String email);

	void deleteByEmail(String email);

	void save(RefreshToken refreshToken);

	void delete(RefreshToken refreshToken);
}
