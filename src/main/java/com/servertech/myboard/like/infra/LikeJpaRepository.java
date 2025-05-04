package com.servertech.myboard.like.infra;

import com.servertech.myboard.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {
	Long countByTargetId(Long targetId);

	Boolean existsByUserIdAndTargetId(Long userId, Long targetId);
}
