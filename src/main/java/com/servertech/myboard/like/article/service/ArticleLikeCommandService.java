package com.servertech.myboard.like.article.service;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
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
	private final ArticleRepository articleRepository;

	@Transactional
	public void toggleLike(Long articleId, User user) {
		Article article = articleRepository.find(articleId).orElseThrow(() -> new IllegalStateException("Article not found"));
		boolean exists = articleLikeRepository.existsByArticleIdAndUserId(articleId, user.getId());
		if (exists) {
			articleLikeRepository.deleteByArticleIdAndUserId(articleId, user.getId());
			article.unlike();
		} else {
			ArticleLike like = ArticleLike.create(articleId, user);
			articleLikeRepository.save(like);
			article.like();
		}
	}

	@Transactional
	public void delete(Long articleId, Long userId) {
		articleLikeRepository.deleteByArticleIdAndUserId(articleId, userId);
	}
}
