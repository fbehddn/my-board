package com.servertech.myboard.like.comment.application;

import com.servertech.myboard.like.comment.domain.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeQueryService {
	private final CommentLikeRepository commentLikeRepository;

	@Transactional(readOnly = true)
	public Long countByCommentId(Long commentId) {
		return commentLikeRepository.countByCommentId(commentId);
	}
}
