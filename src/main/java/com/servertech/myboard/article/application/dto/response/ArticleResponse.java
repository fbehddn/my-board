package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.Builder;

@Builder
public record ArticleResponse(
	String title,
	String author
) {
	public static ArticleResponse from(Article article) {
		return ArticleResponse.builder()
			.title(article.getTitle())
			.author(article.getAuthor())
			.build();
	}
}
