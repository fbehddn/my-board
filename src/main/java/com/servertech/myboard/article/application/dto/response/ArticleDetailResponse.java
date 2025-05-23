package com.servertech.myboard.article.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.servertech.myboard.article.domain.Article;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleDetailResponse(
	Long id,
	String title,
	String content,
	String author,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime updatedAt,
	long likeCount
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
