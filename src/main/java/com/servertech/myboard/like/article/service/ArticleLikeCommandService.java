package com.servertech.myboard.like.article.service;

import com.servertech.myboard.like.article.domain.ArticleLike;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeCommandService {
	private final ArticleLikeRepository articleLikeRepository;

	@Transactional
	public void save(Long articleId, User user) {
		ArticleLike articleLike = ArticleLike.create(articleId, user);
		articleLikeRepository.save(articleLike);
	}

	@Transactional
	public void delete(Long articleId, Long userId) {
		articleLikeRepository.deleteByArticleIdAndUserId(articleId, userId);
	}
}
