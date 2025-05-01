package com.servertech.myboard.comment.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentListResponse(
	List<CommentDetailResponse> comments
) {
	public static CommentListResponse from(List<CommentDetailResponse> comments) {
		return CommentListResponse.builder()
			.comments(comments)
			.build();
	}
}
