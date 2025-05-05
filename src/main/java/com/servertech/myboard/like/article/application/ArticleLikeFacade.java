package com.servertech.myboard.like.article.application;

import com.servertech.myboard.article.application.service.ArticleQueryService;
import com.servertech.myboard.like.article.service.ArticleLikeCommandService;
import com.servertech.myboard.like.article.service.ArticleLikeQueryService;
import com.servertech.myboard.user.application.service.UserQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikeFacade {
	private final UserQueryService userQueryService;
	private final ArticleQueryService articleQueryService;
	private final ArticleLikeQueryService articleLikeQueryService;
	private final ArticleLikeCommandService articleLikeCommandService;

	public void likeArticle(Long articleId, String username) {
		User user = userQueryService.findByEmail(username);
		articleQueryService.find(articleId);

		boolean alreadyLike = articleLikeQueryService.existsByArticleIdAndUserId(articleId, user.getId());

		if (alreadyLike) {
			articleLikeCommandService.delete(articleId, user.getId());
		} else {
			articleLikeCommandService.save(articleId, user);
		}
	}
}
