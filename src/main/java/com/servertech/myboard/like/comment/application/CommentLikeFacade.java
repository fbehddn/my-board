package com.servertech.myboard.like.comment.application;

import com.servertech.myboard.like.comment.service.CommentLikeCommandService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeFacade {
	private final CommentLikeCommandService commentLikeCommandService;

	public void likeComment(Long commentId, User user) {
		commentLikeCommandService.toggleComment(commentId, user);
	}
}
