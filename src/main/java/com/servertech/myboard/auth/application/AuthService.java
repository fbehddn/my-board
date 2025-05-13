package com.servertech.myboard.auth.application;

import com.servertech.myboard.auth.domain.RefreshToken;
import com.servertech.myboard.auth.domain.RefreshTokenRepository;
import com.servertech.myboard.auth.infra.jwt.JwtProvider;
import com.servertech.myboard.auth.application.dto.response.JwtResponse;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.auth.application.dto.request.LoginRequest;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	public JwtResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new EntityNotFoundException("User not found"));

		if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
			throw new IllegalArgumentException("Wrong password");
		}
		String accessToken = jwtProvider.generateAccessToken(user.getEmail());
		String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

		refreshTokenRepository.findByEmail(user.getEmail()).ifPresent(refreshTokenRepository::delete);
		refreshTokenRepository.save(new RefreshToken(null, user.getEmail(), refreshToken, LocalDateTime.now().plusDays(14)));

		return JwtResponse.from(accessToken, refreshToken);
	}

	public JwtResponse refresh(String oldRefreshToken) {
		if (!jwtProvider.validateToken(oldRefreshToken)) {
			throw new IllegalArgumentException("Invalid refresh token");
		}
		String email = jwtProvider.getClaims(oldRefreshToken).getSubject();
		RefreshToken saved = refreshTokenRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("RefreshToken not found"));

		if (!saved.getToken().equals(oldRefreshToken) || saved.getExpiry().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Refresh token expired");
		}

		String newAccessToken = jwtProvider.generateAccessToken(email);
		String newRefreshToken = jwtProvider.generateRefreshToken(email);
		saved.setToken(newRefreshToken);
		saved.setExpiry(LocalDateTime.now().plusDays(14));
		refreshTokenRepository.save(saved);

		return JwtResponse.from(newAccessToken, newRefreshToken);
	}

	public void logout(String email) {
		refreshTokenRepository.findByEmail(email).ifPresent(refreshTokenRepository::delete);
	}
}
