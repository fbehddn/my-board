package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import lombok.Builder;

@Builder
public record ArticleCommentResponse(
	ArticleDetailResponse articleDetailResponse,
	CommentListResponse commentListResponse
) {
	public static ArticleCommentResponse from(ArticleDetailResponse articleDetailResponse, CommentListResponse commentListResponse) {
		return ArticleCommentResponse.builder()
			.articleDetailResponse(articleDetailResponse)
			.commentListResponse(commentListResponse)
			.build();
	}
}