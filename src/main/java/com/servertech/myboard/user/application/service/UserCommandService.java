package com.servertech.myboard.user.application.service;

import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(UserCreateRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		User user = User.create(request.email(), encodedPassword, request.username());
		userRepository.save(user);
	}
}
