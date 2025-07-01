package com.servertech.myboard.like.article.domain;

public interface ArticleLikeRepository {
	void save(ArticleLike articleLike);

	void deleteByArticleIdAndUserId(Long articleId, Long userId);

	boolean existsByArticleIdAndUserId(Long articleId, Long userId);
}
