package com.servertech.myboard.user.application.controller;

import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.application.UserFacade;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserFacade userFacade;

	@PostMapping("/signup")
	public ResponseEntity<Void> create(@RequestBody UserCreateRequest request) {
		userFacade.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequest request) {
		JwtResponse response = userFacade.login(request);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/my")
	public ResponseEntity<UserDetailResponse> myPage(@AuthenticationPrincipal User user) {
		UserDetailResponse response = userFacade.getUserDetail(user);
		return ResponseEntity.ok().body(response);
	}
}
