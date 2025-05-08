package com.servertech.myboard.auth.application.service;

import com.servertech.myboard.global.exception.UnauthorizedException;
import com.servertech.myboard.user.application.service.UserQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserQueryService userQueryService;

	public User currentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof UserDetails userDetails)) {
			throw new UnauthorizedException("User is not authenticated");
		}
		return userQueryService.findByEmail(userDetails.getUsername());
	}
}