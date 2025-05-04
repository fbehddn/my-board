package com.servertech.myboard.like.infra;

import com.servertech.myboard.like.domain.Like;
import com.servertech.myboard.like.domain.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {
	Long countByTargetIdAndTargetType(Long targetId, TargetType targetType);

	boolean existsByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);

	void deleteByTargetIdAndUserIdAndTargetType(Long articleId, Long userId, TargetType targetType);
}
