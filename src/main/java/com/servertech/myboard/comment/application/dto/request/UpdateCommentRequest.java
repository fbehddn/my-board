package com.servertech.myboard.comment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCommentRequest(
	@Schema(description = "댓글 내용", example = "공감합니다")
	String content
) {
}
