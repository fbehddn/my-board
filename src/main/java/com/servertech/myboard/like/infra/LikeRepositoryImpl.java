package com.servertech.myboard.like.infra;

import com.servertech.myboard.like.domain.Like;
import com.servertech.myboard.like.domain.LikeRepository;
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
	public Long countByTargetId(Long articleId) {
		return likeJpaRepository.countByTargetId(articleId);
	}

	@Override
	public Boolean existsByUserIdAndTargetId(Long userId, Long targetId) {
		return likeJpaRepository.existsByUserIdAndTargetId(userId, targetId);
	}
}
