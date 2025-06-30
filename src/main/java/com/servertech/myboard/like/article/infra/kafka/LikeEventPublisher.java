package com.servertech.myboard.like.article.infra.kafka;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.application.event.LikeEventOutboxService;
import com.servertech.myboard.like.article.domain.LikeEventOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeEventPublisher {
	private final KafkaTemplate<String, LikeChange> kafkaTemplate;
	private final LikeEventOutboxService outboxService;
	//	private final LikeEventOutboxRepository outboxRepository;
	private static final String TOPIC = "like-change";

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publishLikeEvent(LikeChange change) {
//		LikeEventOutbox outbox = outboxRepository.save(LikeEventOutbox.from(change));
		String key = change.articleId().toString();
		ProducerRecord<String, LikeChange> record = new ProducerRecord<>(TOPIC, key, change);
		CompletableFuture<SendResult<String, LikeChange>> future = kafkaTemplate.send(record);

		future.whenComplete((result, excepion) -> {
			if (excepion == null) {
				RecordMetadata meta = result.getRecordMetadata();
				log.info("send successfully to topic {} partition {} at offset {}",
					meta.topic(), meta.partition(), meta.offset());
			} else {
				log.error("failed to send LikeChange event (articleId={} userId={}): {}",
					change.articleId(), change.userId(), excepion.getMessage(), excepion);
				outboxService.saveLikeEvent(change);
			}
		});
	}

	@Scheduled(fixedDelay = 60000)
	public void publishPendingOutboxEvents() {
		List<LikeEventOutbox> pending = outboxService.findAll();
		for (LikeEventOutbox event : pending) {
			CompletableFuture<SendResult<String, LikeChange>> future = kafkaTemplate.send(TOPIC, event.getArticleId().toString(), toEvent(event));
			future.whenComplete((result, exception) -> {
				if (exception == null) {
					outboxService.deleteById(event.getId());
				}
			});
		}
	}

	private LikeChange toEvent(LikeEventOutbox event) {
		return new LikeChange(event.getArticleId(), event.getUserId(), event.isAdded());
	}
}