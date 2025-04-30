package com.servertech.myboard.user.application.controller;

import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.UserResponse;
import com.servertech.myboard.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public UserResponse create(@RequestBody UserCreateRequest request) {
		return userService.create(request);
	}

	@PostMapping("/login")
	public UserResponse login(@RequestBody UserLoginRequest request) {
		return userService.login(request);
	}

	@GetMapping("/my")
	public UserDetailResponse myPage(){
		return userService.myPage();
	}
}
