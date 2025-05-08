package com.servertech.myboard.like.comment.infra.persistence.jpa;

import com.servertech.myboard.like.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLike, Long> {
	Long countByCommentId(Long commentId);

	boolean existsByCommentIdAndUserId(Long commentId, Long userId);

	void deleteByCommentIdAndUserId(Long commentId, Long userId);
}
