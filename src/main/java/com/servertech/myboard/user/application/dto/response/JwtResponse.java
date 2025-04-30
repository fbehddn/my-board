package com.servertech.myboard.user.application.dto.response;

import lombok.Builder;

@Builder
public record JwtResponse(
	String accessToken
) {
	public static JwtResponse from(String accessToken) {
		return JwtResponse.builder()
			.accessToken(accessToken)
			.build();
	}
}
