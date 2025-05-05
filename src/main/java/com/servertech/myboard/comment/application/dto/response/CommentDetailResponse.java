package com.servertech.myboard.comment.application.dto.response;

import com.servertech.myboard.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentDetailResponse(
	String content,
	String username,
	Long likeCount
) {
	public static CommentDetailResponse from(Comment comment, Long likeCount) {
		return CommentDetailResponse.builder()
			.content(comment.getContent())
			.username(comment.getAuthor())
			.likeCount(likeCount)
			.build();
	}
}
