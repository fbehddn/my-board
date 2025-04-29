package com.servertech.myboard.article.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ArticleListResponse(
	List<ArticleDetailResponse> articles
) {
	public static ArticleListResponse from(List<ArticleDetailResponse> articles) {
		return ArticleListResponse.builder()
			.articles(articles)
			.build();
	}
}
