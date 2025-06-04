package com.servertech.myboard.article.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleListResponse implements Serializable {
	private List<ArticleResponse> articles;

	public static ArticleListResponse from(List<ArticleResponse> articles) {
		return ArticleListResponse.builder()
			.articles(articles)
			.build();
	}
}