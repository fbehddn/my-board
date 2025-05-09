package com.servertech.myboard.user.application.auth;

import com.servertech.myboard.auth.infra.jwt.JwtProvider;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public JwtResponse login(UserLoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new EntityNotFoundException("User not found"));

		if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
			throw new IllegalArgumentException("Wrong password");
		}
		String token = jwtProvider.generateToken(user.getEmail());
		return new JwtResponse(token);
	}
}
