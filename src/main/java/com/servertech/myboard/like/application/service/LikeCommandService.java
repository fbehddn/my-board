package com.servertech.myboard.like.application.service;

import com.servertech.myboard.like.domain.Like;
import com.servertech.myboard.like.domain.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCommandService {
	private final LikeRepository likeRepository;

	public void save(Like like) {
		likeRepository.save(like);
	}
}
