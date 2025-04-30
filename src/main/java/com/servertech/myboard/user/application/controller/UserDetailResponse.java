package com.servertech.myboard.user.application.controller;

import com.servertech.myboard.user.domain.User;
import lombok.Builder;

@Builder
public record UserDetailResponse(
	String email,
	String password,
	String username
) {
	public static UserDetailResponse from(User user) {
		return UserDetailResponse.builder()
			.email(user.getEmail())
			.password(user.getPassword())
			.username(user.getUsername())
			.build();
	}
}
