package com.servertech.myboard.like.domain;

public interface LikeRepository {
	void save(Like like);

	Long countByTargetIdAndTargetType(Long articleId, TargetType targetType);

	boolean existsByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

	void deleteByTargetIdAndUserIdAndTargetType(Long targetId, Long userId, TargetType targetType);
}
