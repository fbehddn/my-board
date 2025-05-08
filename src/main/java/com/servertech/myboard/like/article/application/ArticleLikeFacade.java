package com.servertech.myboard.like.article.application;

import com.servertech.myboard.like.article.service.ArticleLikeCommandService;
import com.servertech.myboard.user.application.service.UserQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikeFacade {
	private final UserQueryService userQueryService;
	private final ArticleLikeCommandService articleLikeCommandService;

	@CacheEvict(value = "articles", allEntries = true)
	public void likeArticle(Long articleId, String username) {
		User user = userQueryService.findByEmail(username);
		articleLikeCommandService.toggleLike(articleId, user);
	}
}
