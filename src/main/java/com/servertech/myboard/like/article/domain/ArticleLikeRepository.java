package com.servertech.myboard.like.article.domain;

public interface ArticleLikeRepository {
	void save(ArticleLike articleLike);

	Long countByArticleId(Long articleId);

	boolean existsByArticleIdAndUserId(Long articleId, Long userId);

	void deleteByArticleIdAndUserId(Long articleId, Long userId);
}
