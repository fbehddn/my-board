package com.servertech.myboard.user.application.service;

import com.servertech.myboard.user.application.controller.UserDetailResponse;
import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.UserResponse;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserResponse create(UserCreateRequest request) {
		User user = User.create(request.email(), request.password(), request.username());
		userRepository.save(user);
		return UserResponse.from(user);
	}

	public UserResponse login(UserLoginRequest request) {
		User findUser = userRepository.findByEmail(request.email());
		if (findUser != null && findUser.getPassword().equals(request.password())) {
			return UserResponse.from(findUser);
		}
		return null;
	}

	public UserDetailResponse myPage() {
		return null;
	}
}
