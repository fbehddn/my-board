package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse implements Serializable {
	private Long id;
	private String title;
	private String author;

	public static ArticleResponse from(Article article) {
		return ArticleResponse.builder()
			.id(article.getId())
			.title(article.getTitle())
			.author(article.getAuthor())
			.build();
	}
}
