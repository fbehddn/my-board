package com.servertech.myboard.like.application.service;

import com.servertech.myboard.like.domain.Like;
import com.servertech.myboard.like.domain.LikeRepository;
import com.servertech.myboard.like.domain.TargetType;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeCommandService {
	private final LikeRepository likeRepository;

	@Transactional
	public void save(Long articleId, User user, TargetType targetType) {
		Like like = Like.create(articleId, user, targetType);
		likeRepository.save(like);
	}

	@Transactional
	public void delete(Long targetId, Long userId, TargetType targetType) {
		likeRepository.deleteByTargetIdAndUserIdAndTargetType(targetId, userId, targetType);
	}
}
