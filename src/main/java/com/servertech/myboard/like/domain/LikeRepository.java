package com.servertech.myboard.like.domain;

public interface LikeRepository {
	void save(Like like);

	Long countByTargetId(Long articleId);

	Boolean existsByUserIdAndTargetId(Long userId, Long targetId);
}
