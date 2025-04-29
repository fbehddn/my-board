package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleDetailResponse(
	String title,
	String content,
	String author,
	LocalDateTime updatedAt
) {
	public static ArticleDetailResponse from(Article article) {
		return ArticleDetailResponse.builder()
			.title(article.getTitle())
			.content(article.getContent())
			.author(article.getAuthor())
			.updatedAt(article.getUpdatedAt())
			.build();
	}
}
