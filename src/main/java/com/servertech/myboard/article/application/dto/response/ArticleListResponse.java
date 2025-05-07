package com.servertech.myboard.article.application.dto.response;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record ArticleListResponse(
	List<ArticleResponse> articles
) implements Serializable {
	public static ArticleListResponse from(List<ArticleResponse> articles) {
		return ArticleListResponse.builder()
			.articles(articles)
			.build();
	}
}
