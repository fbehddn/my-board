package com.servertech.myboard.user.application.dto.request;

public record UserLoginRequest(
	String email,
	String password
) {
}
