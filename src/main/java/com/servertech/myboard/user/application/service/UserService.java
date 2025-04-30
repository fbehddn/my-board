package com.servertech.myboard.user.application.service;

import com.servertech.myboard.jwt.JwtProvider;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public void create(UserCreateRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		User user = User.create(request.email(), encodedPassword, request.username());
		userRepository.save(user);
	}

	public JwtResponse login(UserLoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new IllegalArgumentException("Wrong password");
		}

		String token = jwtProvider.generateToken(user.getId());

		return JwtResponse.builder()
			.accessToken(token)
			.build();
	}

	public UserDetailResponse myPage() {
		return null;
	}
}
