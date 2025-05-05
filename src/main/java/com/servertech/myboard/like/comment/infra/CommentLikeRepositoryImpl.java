package com.servertech.myboard.like.comment.infra;

import com.servertech.myboard.like.comment.domain.CommentLike;
import com.servertech.myboard.like.comment.domain.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {
	private final CommentLikeJpaRepository commentLikeJpaRepository;

	@Override
	public void save(CommentLike commentLike) {
		commentLikeJpaRepository.save(commentLike);
	}

	@Override
	public Long countByCommentId(Long commentId) {
		return commentLikeJpaRepository.countByCommentId(commentId);
	}

	@Override
	public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
		return commentLikeJpaRepository.existsByCommentIdAndUserId(commentId, userId);
	}

	@Override
	public void deleteByCommentIdAndUserId(Long commentId, Long userId) {
		commentLikeJpaRepository.deleteByCommentIdAndUserId(commentId, userId);
	}
}
