package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleDetailResponse(
	Long id,
	String title,
	String content,
	String author,
	LocalDateTime updatedAt,
	Long likeCount
) {
	public static ArticleDetailResponse from(Article article, Long likeCount) {
		return ArticleDetailResponse.builder()
			.id(article.getId())
			.title(article.getTitle())
			.content(article.getContent())
			.author(article.getAuthor())
			.updatedAt(article.getUpdatedAt())
			.likeCount(likeCount)
			.build();
	}
}
