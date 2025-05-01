package com.servertech.myboard.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginRequest(

	@Schema(description = "사용자 이메일", example = "email@google.com")
	String email,

	@Schema(description = "사용자 비밀번호", example = "password1234")
	String password
) {
}
