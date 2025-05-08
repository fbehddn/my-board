package com.servertech.myboard.like.article.infra.persistence.jpa;

import com.servertech.myboard.like.article.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeJpaRepository extends JpaRepository<ArticleLike, Long> {
	Long countByArticleId(Long articleId);

	boolean existsByArticleIdAndUserId(Long articleId, Long userId);

	void deleteByArticleIdAndUserId(Long articleId, Long userId);
}
