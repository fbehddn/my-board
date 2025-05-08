package com.servertech.myboard.like.article.infra.persistence.jpa;

import com.servertech.myboard.like.article.domain.ArticleLike;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ArticleLikeJpaAdapter implements ArticleLikeRepository {
	private final ArticleLikeJpaRepository articleLikeJpaRepository;

	@Override
	public void save(ArticleLike articleLike) {
		articleLikeJpaRepository.save(articleLike);
	}

	@Override
	public Long countByArticleId(Long articleId) {
		return articleLikeJpaRepository.countByArticleId(articleId);
	}

	@Override
	public boolean existsByArticleIdAndUserId(Long articleId, Long userId) {
		return articleLikeJpaRepository.existsByArticleIdAndUserId(articleId, userId);
	}

	@Override
	public void deleteByArticleIdAndUserId(Long articleId, Long userId) {
		articleLikeJpaRepository.deleteByArticleIdAndUserId(articleId, userId);
	}
}
