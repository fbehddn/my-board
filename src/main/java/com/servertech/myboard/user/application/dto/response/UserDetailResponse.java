package com.servertech.myboard.user.application.dto.response;

import com.servertech.myboard.user.domain.User;
import lombok.Builder;

@Builder
public record UserDetailResponse(
	String email,
	String username
) {
	public static UserDetailResponse from(User user) {
		return UserDetailResponse.builder()
			.email(user.getEmail())
			.username(user.getUsername())
			.build();
	}
}
