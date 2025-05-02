package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.comment.application.dto.response.CommentDetailResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ArticleDetailResponse(
	String title,
	String content,
	String author,
	LocalDateTime updatedAt,
	List<CommentDetailResponse> comments
) {
	public static ArticleDetailResponse from(Article article) {
		return ArticleDetailResponse.builder()
			.title(article.getTitle())
			.content(article.getContent())
			.author(article.getAuthor())
			.updatedAt(article.getUpdatedAt())
			.comments(article.getComments().stream().map(CommentDetailResponse::from).collect(Collectors.toList()))
			.build();
	}
}
