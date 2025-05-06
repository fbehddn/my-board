package com.servertech.myboard.like.comment.service;

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

	@Transactional
	public void save(Long commentId, User user) {
		CommentLike commentLike = CommentLike.create(commentId, user);
		commentLikeRepository.save(commentLike);
	}

	@Transactional
	public void delete(Long commentId, Long userId) {
		commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
	}
}
