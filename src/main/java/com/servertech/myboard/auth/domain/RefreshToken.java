package com.servertech.myboard.auth.domain;

import lombok.Builder;

import java.time.Duration;

@Builder
public record RefreshToken(
	String email,
	String value,
	Duration ttl
) {
	public static RefreshToken from(String email, String value, Duration ttl) {
		return RefreshToken.builder()
			.email(email)
			.value(value)
			.ttl(ttl)
			.build();
	}
}
