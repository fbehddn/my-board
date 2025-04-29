package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.Builder;

@Builder
public record ArticleResponse(
	Long articleId
) {
	public static ArticleResponse from(Article savedArticle) {
		return ArticleResponse.builder()
			.articleId(savedArticle.getId())
			.build();
	}
}
