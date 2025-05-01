package com.servertech.myboard.comment.application.dto.response;

import com.servertech.myboard.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentResponse(
	Long id
) {
	public static CommentResponse from(Comment comment) {
		return CommentResponse.builder()
			.id(comment.getId())
			.build();
	}
}
