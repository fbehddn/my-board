package com.servertech.myboard.like.application;

import com.servertech.myboard.article.application.service.ArticleQueryService;
import com.servertech.myboard.comment.application.service.CommentQueryService;
import com.servertech.myboard.like.application.service.LikeCommandService;
import com.servertech.myboard.like.application.service.LikeQueryService;
import com.servertech.myboard.like.domain.TargetType;
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
	private final LikeQueryService likeQueryService;
	private final CommentQueryService commentQueryService;

	public void likeArticle(Long articleId, String username) {
		User user = userQueryService.findByEmail(username);
		articleQueryService.find(articleId);

		boolean alreadyLike = likeQueryService.existsByUserIdAndTargetIdAndTargetType(user.getId(), articleId, TargetType.ARTICLE);

		if (alreadyLike) {
			likeCommandService.delete(articleId, user.getId(), TargetType.ARTICLE);
		} else likeCommandService.save(articleId, user, TargetType.ARTICLE);
	}

	public void likeComment(Long commentId, String username) {
		User user = userQueryService.findByEmail(username);
		commentQueryService.find(commentId);

		boolean alreadyLike = likeQueryService.existsByUserIdAndTargetIdAndTargetType(user.getId(), commentId, TargetType.COMMENT);

		if (alreadyLike) {
			likeCommandService.delete(commentId, user.getId(), TargetType.COMMENT);
		} else likeCommandService.save(commentId, user, TargetType.COMMENT);
	}
}
