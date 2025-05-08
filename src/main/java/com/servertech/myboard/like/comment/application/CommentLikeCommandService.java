package com.servertech.myboard.like.comment.application;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.like.comment.domain.CommentLike;
import com.servertech.myboard.like.comment.domain.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeCommandService {
	private final CommentLikeRepository commentLikeRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public void likeComment(Long commentId, Long userId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		boolean exists = commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
		if (exists) {
			commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
			comment.unlike();
		} else {
			CommentLike like = CommentLike.create(commentId, userId);
			commentLikeRepository.save(like);
			comment.like();
		}
	}
}
