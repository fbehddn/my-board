package com.servertech.myboard.like.application.service;

import com.servertech.myboard.global.exception.DuplicateLikeException;
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

	public void validateNotAlreadyLiked(Long userId, Long articleId) {
		if (likeRepository.existsByUserIdAndTargetId(userId, articleId)) {
			throw new DuplicateLikeException("해당 게시글을 이미 좋아요 했습니다");
		}
	}
}
