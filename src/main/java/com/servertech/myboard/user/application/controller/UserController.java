package com.servertech.myboard.user.application.controller;

import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.application.UserFacade;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserFacade userFacade;

	@PostMapping("/signup")
	public void create(@RequestBody UserCreateRequest request) {
		userFacade.create(request);
	}

	@PostMapping("/login")
	public JwtResponse login(@RequestBody UserLoginRequest request) {
		return userFacade.login(request);
	}

	@GetMapping("/my")
	public UserDetailResponse myPage(@AuthenticationPrincipal User user) {
		return userFacade.getUserDetail(user);
	}
}
