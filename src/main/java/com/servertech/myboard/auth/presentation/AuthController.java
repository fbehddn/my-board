package com.servertech.myboard.auth.presentation;

import com.servertech.myboard.auth.application.dto.response.JwtResponse;
import com.servertech.myboard.auth.application.AuthService;
import com.servertech.myboard.auth.application.dto.request.LoginRequest;
import com.servertech.myboard.user.infra.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
		JwtResponse response = authService.login(request);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtResponse> refresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
		JwtResponse response = authService.refresh(refreshToken);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails user) {
		authService.logout(user.getUsername());
		return ResponseEntity.noContent().build();
	}
}
