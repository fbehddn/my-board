package com.servertech.myboard.like.article.infra.kafka;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeEventPublisher {
	private final KafkaTemplate<String, LikeChange> kafkaTemplate;
	private static final String TOPIC = "like-change";

	public void publishLikeEvent(LikeChange change) {
		kafkaTemplate.send(TOPIC, change.articleId().toString(), change);
	}
}