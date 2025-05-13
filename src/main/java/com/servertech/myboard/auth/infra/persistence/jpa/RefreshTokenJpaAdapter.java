package com.servertech.myboard.auth.infra.persistence.jpa;

import com.servertech.myboard.auth.domain.RefreshToken;
import com.servertech.myboard.auth.domain.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenJpaAdapter implements RefreshTokenRepository {
	private final RefreshTokenJpaRepository refreshTokenJpaRepository;

	@Override
	public Optional<RefreshToken> findByEmail(String email) {
		return refreshTokenJpaRepository.findByEmail(email);
	}

	@Override
	public void deleteByEmail(String email) {
		refreshTokenJpaRepository.deleteByEmail(email);
	}

	@Override
	public void save(RefreshToken refreshToken) {
		refreshTokenJpaRepository.save(refreshToken);
	}

	@Override
	public void delete(RefreshToken refreshToken) {
		refreshTokenJpaRepository.delete(refreshToken);
	}
}
