package com.servertech.myboard.like.article.application;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.domain.LikeEventOutbox;
import com.servertech.myboard.like.article.domain.LikeEventOutboxRepository;
import com.servertech.myboard.like.article.infra.kafka.LikeEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class LikeEventOutboxService {
	private final LikeEventOutboxRepository likeEventOutboxRepository;
	private final LikeEventPublisher likeEventPublisher;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void saveLikeEvent(LikeChange c) {
		LikeEventOutbox likeEventOutbox = LikeEventOutbox.create(c.articleId(), c.userId(), c.added());
		likeEventOutboxRepository.save(likeEventOutbox);
	}
}
