package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ArticleResponse(
	Long id,
	String title,
	String author
) implements Serializable {
	public static ArticleResponse from(Article article) {
		return ArticleResponse.builder()
			.id(article.getId())
			.title(article.getTitle())
			.author(article.getAuthor())
			.build();
	}
}
