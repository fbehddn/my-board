package com.servertech.myboard.like.comment.application;

import com.servertech.myboard.comment.application.service.CommentQueryService;
import com.servertech.myboard.like.comment.service.CommentLikeCommandService;
import com.servertech.myboard.like.comment.service.CommentLikeQueryService;
import com.servertech.myboard.user.application.service.UserQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeFacade {
	private final UserQueryService userQueryService;
	private final CommentQueryService commentQueryService;
	private final CommentLikeQueryService commentLikeQueryService;
	private final CommentLikeCommandService commentLikeCommandService;

	public void likeComment(Long commentId, String username) {
		User user = userQueryService.findByEmail(username);
		commentQueryService.find(commentId);

		boolean alreadyLike = commentLikeQueryService.existsByCommentIdAndUserId(commentId, user.getId());

		if (alreadyLike) {
			commentLikeCommandService.delete(commentId, user.getId());
		} else {
			commentLikeCommandService.save(commentId, user);
		}
	}
}
