package com.servertech.myboard.like.article.domain;

import org.springframework.data.repository.query.Param;

public interface ArticleLikeRepository {
	void save(ArticleLike articleLike);

	Long countByArticleId(Long articleId);

	void deleteByArticleIdAndUserId(Long articleId, Long userId);

	void insertIgnore(@Param("articleId") Long articleId, @Param("userId") Long userId);

	boolean existsByArticleIdAndUserId(Long articleId, Long userId);
}
