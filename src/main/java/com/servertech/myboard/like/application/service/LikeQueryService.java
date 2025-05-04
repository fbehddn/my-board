package com.servertech.myboard.like.application.service;

import com.servertech.myboard.like.domain.LikeRepository;
import com.servertech.myboard.like.domain.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeQueryService {
	private final LikeRepository likeRepository;

	@Transactional(readOnly = true)
	public Long countByTargetIdAndTargetType(Long articleId, TargetType targetType) {
		return likeRepository.countByTargetIdAndTargetType(articleId, targetType);
	}

	@Transactional(readOnly = true)
	public boolean existsByUserIdAndTargetIdAndTargetType(Long id, Long articleId, TargetType targetType) {
		return likeRepository.existsByUserIdAndTargetIdAndTargetType(id, articleId, targetType);
	}
}
