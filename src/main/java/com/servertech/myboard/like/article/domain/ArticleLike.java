package com.servertech.myboard.like.article.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "article_likes",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_article_like_article_user",
		columnNames = {"article_id", "user_id"}
	)
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "article_id", nullable = false)
	private Long articleId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	public static ArticleLike create(Long articleId, Long userId) {
		return ArticleLike.builder()
			.articleId(articleId)
			.userId(userId)
			.build();
	}
}
