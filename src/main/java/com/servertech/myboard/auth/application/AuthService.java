package com.servertech.myboard.auth.application;

import com.servertech.myboard.auth.domain.RefreshToken;
import com.servertech.myboard.auth.domain.RefreshTokenStore;
import com.servertech.myboard.auth.infra.jwt.JwtProvider;
import com.servertech.myboard.auth.application.dto.response.JwtResponse;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.auth.application.dto.request.LoginRequest;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenStore refreshTokenStore;

	private static final Duration RT_TTL = Duration.ofDays(14);


	public JwtResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new EntityNotFoundException("User not found"));

		if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
			throw new IllegalArgumentException("Wrong password");
		}
		String accessToken = jwtProvider.generateAccessToken(user.getEmail());
		String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

		RefreshToken rt = RefreshToken.from(request.email(), refreshToken, RT_TTL);
		refreshTokenStore.save(rt);

		return JwtResponse.from(accessToken, refreshToken);
	}

	public JwtResponse refresh(String oldRefreshToken) {
		if (!jwtProvider.validateToken(oldRefreshToken)) {
			throw new IllegalArgumentException("Invalid refresh token");
		}
		String email = jwtProvider.getClaims(oldRefreshToken).getSubject();
		String saved = refreshTokenStore.find(email).orElseThrow(() -> new EntityNotFoundException("Refresh token not found"));

		if (!saved.equals(oldRefreshToken)) {
			throw new IllegalArgumentException("Wrong refresh token");
		}

		String newAccessToken = jwtProvider.generateAccessToken(email);
		String newRefreshToken = jwtProvider.generateRefreshToken(email);

		RefreshToken rt = RefreshToken.from(email, newRefreshToken, RT_TTL);

		refreshTokenStore.save(rt);

		return JwtResponse.from(newAccessToken, newRefreshToken);
	}

	public void logout(String email) {
		refreshTokenStore.delete(email);
	}
}
