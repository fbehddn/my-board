package com.servertech.myboard.user.application.dto.response;

import com.servertech.myboard.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(
	Long userId
) {
	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getId())
			.build();
	}
}
