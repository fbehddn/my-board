package com.servertech.myboard.user.application.service;

import com.servertech.myboard.auth.infra.jwt.JwtProvider;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public JwtResponse login(UserLoginRequest request, User user) {
		if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
			throw new IllegalArgumentException("Wrong password");
		}
		String token = jwtProvider.generateToken(user.getEmail());

		return JwtResponse.builder()
			.accessToken(token)
			.build();
	}

	public UserDetailResponse getUserDetail(User user) {
		return UserDetailResponse.from(user);
	}

	public User findByEmail(String username) {
		return userRepository.findByEmail(username)
			.orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username));
	}
}

