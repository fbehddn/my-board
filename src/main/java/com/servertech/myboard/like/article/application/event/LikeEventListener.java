package com.servertech.myboard.like.article.application.event;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.infra.kafka.LikeEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LikeEventListener {
	private final LikeEventPublisher publisher;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishEvent(LikeChange likeChange) {
		publisher.publishLikeEvent(likeChange);
	}
}
