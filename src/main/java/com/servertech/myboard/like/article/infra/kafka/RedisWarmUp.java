package com.servertech.myboard.like.article.infra.kafka;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisWarmUp {
	private static final String TOPIC = "like-change";
	private final StringRedisTemplate redisTemplate;
	private final KafkaConsumer<String, LikeChange> warmupConsumer;

	@EventListener(ContextRefreshedEvent.class)
	public void onStart(ContextRefreshedEvent event) {
		Set<String> existingKeys = redisTemplate.keys("article:likes:*");
		if (existingKeys != null && !existingKeys.isEmpty()) {
			return;
		}

		//레디스에 데이터가 없으면
		List<PartitionInfo> partitions = warmupConsumer.partitionsFor(TOPIC);
		if (partitions == null || partitions.isEmpty()) {
			return;
		}

		List<TopicPartition> topicPartitions = partitions.stream()
			.map(pi -> new TopicPartition(pi.topic(), pi.partition()))
			.toList();

		warmupConsumer.assign(topicPartitions);
		warmupConsumer.seekToBeginning(topicPartitions);

		//while 루프를 통해 모든 메시지 읽어서 Redis에 복원
		while (true) {
			ConsumerRecords<String, LikeChange> records = warmupConsumer.poll(Duration.ofMillis(500));
			if (records.isEmpty()) break;
			for (ConsumerRecord<String, LikeChange> r : records) {
				LikeChange lc = r.value();
				String redisKey = "article:likes:" + lc.articleId();
				if (lc.added()) {
					redisTemplate.boundSetOps(redisKey).add(lc.userId().toString());
				} else {
					redisTemplate.boundSetOps(redisKey).remove(lc.userId().toString());
				}
			}
		}
		warmupConsumer.close();
	}
}
