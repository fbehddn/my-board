package com.servertech.myboard.article.application.dto.response;

import com.servertech.myboard.article.domain.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
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
		return new ArticleDetailResponse(
			article.getId(),
			article.getTitle(),
			article.getContent(),
			article.getAuthor(),
			article.getUpdatedAt(),
			likeCount
		);
	}
}
