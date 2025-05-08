package com.servertech.myboard.like.comment.service;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.like.comment.domain.CommentLike;
import com.servertech.myboard.like.comment.domain.CommentLikeRepository;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeCommandService {
	private final CommentLikeRepository commentLikeRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public void toggleComment(Long commentId, User user) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		boolean exists = commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId());
		if (exists) {
			commentLikeRepository.deleteByCommentIdAndUserId(commentId, user.getId());
			comment.unlike();
		}else {
			CommentLike like = CommentLike.create(commentId, user);
			commentLikeRepository.save(like);
			comment.like();
		}
	}
}
