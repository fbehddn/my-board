package com.servertech.myboard.user.application.dto.request;

public record UserCreateRequest(
	String email,
	String password,
	String username
) {
}
