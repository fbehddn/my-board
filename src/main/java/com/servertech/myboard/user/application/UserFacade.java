package com.servertech.myboard.user.application;

import com.servertech.myboard.auth.application.service.AuthService;
import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.application.service.UserCommandService;
import com.servertech.myboard.user.application.service.UserQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
	private final UserQueryService userQueryService;
	private final UserCommandService userCommandService;

	public void create(UserCreateRequest request) {
		userCommandService.create(request);
	}

	public JwtResponse login(UserLoginRequest request) {
		User user = userQueryService.findByEmail(request.email());
		return userQueryService.login(request, user);
	}

	public UserDetailResponse getUserDetail(User user) {
		return userQueryService.getUserDetail(user);
	}
}
