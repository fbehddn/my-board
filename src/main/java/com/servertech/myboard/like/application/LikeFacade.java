package com.servertech.myboard.like.application;

import com.servertech.myboard.article.application.service.ArticleQueryService;
import com.servertech.myboard.like.application.service.LikeCommandService;
import com.servertech.myboard.like.domain.Like;
import com.servertech.myboard.user.application.service.UserQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeFacade {
	private final UserQueryService userQueryService;
	private final LikeCommandService likeCommandService;
	private final ArticleQueryService articleQueryService;

	public void like(Long articleId, String username) {
		User user = userQueryService.findByEmail(username);
		if (articleQueryService.find(articleId) != null) {
			Like like = Like.create(articleId, user);
			likeCommandService.save(like);
		}
	}
}
