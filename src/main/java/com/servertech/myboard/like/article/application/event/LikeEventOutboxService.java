package com.servertech.myboard.like.article.application.event;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.domain.LikeEventOutbox;
import com.servertech.myboard.like.article.domain.LikeEventOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class LikeEventOutboxService {
	private final LikeEventOutboxRepository outboxRepository;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void saveLikeEvent(LikeChange likeChange) {
		outboxRepository.save(LikeEventOutbox.from(likeChange));
	}
}
