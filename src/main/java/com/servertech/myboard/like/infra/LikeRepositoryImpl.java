package com.servertech.myboard.like.infra;

import com.servertech.myboard.like.domain.Like;
import com.servertech.myboard.like.domain.LikeRepository;
import com.servertech.myboard.like.domain.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {
	private final LikeJpaRepository likeJpaRepository;

	@Override
	public void save(Like like) {
		likeJpaRepository.save(like);
	}

	@Override
	public Long countByTargetIdAndTargetType(Long articleId, TargetType targetType) {
		return likeJpaRepository.countByTargetIdAndTargetType(articleId, targetType);
	}

	@Override
	public boolean existsByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType) {
		return likeJpaRepository.existsByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
	}

	@Override
	public void deleteByTargetIdAndUserIdAndTargetType(Long targetId, Long userId, TargetType targetType) {
		likeJpaRepository.deleteByTargetIdAndUserIdAndTargetType(targetId, userId, targetType);
	}
}
