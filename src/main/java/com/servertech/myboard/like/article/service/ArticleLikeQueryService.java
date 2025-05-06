package com.servertech.myboard.like.article.service;

import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeQueryService {
	private final ArticleLikeRepository articleLikeRepository;

	@Transactional(readOnly = true)
	public Long countByArticleId(Long articleId) {
		return articleLikeRepository.countByArticleId(articleId);
	}

	@Transactional(readOnly = true)
	public boolean existsByArticleIdAndUserId(Long articleId, Long userId) {
		return articleLikeRepository.existsByArticleIdAndUserId(articleId, userId);
	}
}
