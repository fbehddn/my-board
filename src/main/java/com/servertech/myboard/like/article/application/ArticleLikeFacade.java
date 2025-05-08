package com.servertech.myboard.like.article.application;

import com.servertech.myboard.like.article.service.ArticleLikeCommandService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikeFacade {
	private final ArticleLikeCommandService articleLikeCommandService;

	@CacheEvict(value = "articles", allEntries = true)
	public void likeArticle(Long articleId, User user) {
		articleLikeCommandService.toggleLike(articleId, user);
	}
}
