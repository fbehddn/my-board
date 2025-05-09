package com.servertech.myboard.user.presentation;

import com.servertech.myboard.user.application.dto.request.UserCreateRequest;
import com.servertech.myboard.user.application.dto.request.UserLoginRequest;
import com.servertech.myboard.user.application.dto.response.JwtResponse;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.application.auth.UserAuthService;
import com.servertech.myboard.user.application.command.UserCommandService;
import com.servertech.myboard.user.application.query.UserQueryService;
import com.servertech.myboard.user.infra.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserCommandService userCommandService;
	private final UserAuthService userAuthService;
	private final UserQueryService userQueryService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signUp(@RequestBody UserCreateRequest request) {
		userCommandService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequest request) {
		JwtResponse response = userAuthService.login(request);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/my")
	public ResponseEntity<UserDetailResponse> myPage(@AuthenticationPrincipal CustomUserDetails principal) {
		UserDetailResponse response = userQueryService.getUserDetail(principal.getId());
		return ResponseEntity.ok().body(response);
	}
}
