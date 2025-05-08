package com.servertech.myboard.like.article.domain;

import com.servertech.myboard.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLike extends BaseTimeEntity {
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
