package com.servertech.myboard.like.comment.domain;

public interface CommentLikeRepository {
	void save(CommentLike commentLike);

	Long countByCommentId(Long commentId);

	boolean existsByCommentIdAndUserId(Long commentId, Long userId);

	void deleteByCommentIdAndUserId(Long commentId, Long userId);
}
