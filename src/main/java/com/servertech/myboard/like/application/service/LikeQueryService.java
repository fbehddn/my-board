package com.servertech.myboard.like.application.service;

import com.servertech.myboard.like.domain.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeQueryService {
	private final LikeRepository likeRepository;

	public Long countByArticleId(Long articleId) {
		return likeRepository.countByTargetId(articleId);
	}
}
