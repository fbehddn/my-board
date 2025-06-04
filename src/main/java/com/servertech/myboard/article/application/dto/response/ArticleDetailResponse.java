package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailResponse {
	private Long id;
	private String title;
	private String content;
	private String author;
	private LocalDateTime updatedAt;
	private long likeCount;

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
