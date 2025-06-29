package com.servertech.myboard.auth.application.dto.response;

import lombok.Builder;

@Builder
public record JwtResponse(
	String accessToken,
	String refreshToken
) {
	public static JwtResponse from(String accessToken, String refreshToken) {
		return JwtResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
