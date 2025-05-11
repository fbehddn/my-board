package com.servertech.myboard.like.article.application;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.like.article.domain.ArticleLike;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
	private final ArticleLikeRepository articleLikeRepository;
	private final ArticleRepository articleRepository;

	@CacheEvict(value = "articles", allEntries = true)
	@Transactional
	public void likeArticle(Long articleId, Long userId) {
		Article article = articleRepository.find(articleId).orElseThrow(() -> new EntityNotFoundException("Article not found"));
		boolean exists = articleLikeRepository.existsByArticleIdAndUserId(articleId, userId);

		if (exists) {
			articleLikeRepository.deleteByArticleIdAndUserId(articleId, userId);
			article.unlike();
		} else {
			ArticleLike like = ArticleLike.create(articleId, userId);
			articleLikeRepository.save(like);
			article.like();
		}
	}
}
