package com.servertech.myboard.like.article.application.event;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.domain.LikeEventOutbox;
import com.servertech.myboard.like.article.domain.LikeEventOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeEventOutboxService {
	private final LikeEventOutboxRepository outboxRepository;

	@Transactional
	public LikeEventOutbox saveLikeEvent(LikeChange likeChange) {
		return outboxRepository.save(LikeEventOutbox.from(likeChange));
	}

	public List<LikeEventOutbox> findAll() {
		return outboxRepository.findAll();
	}

	public void deleteById(Long id) {
		outboxRepository.deleteById(id);
	}
}
