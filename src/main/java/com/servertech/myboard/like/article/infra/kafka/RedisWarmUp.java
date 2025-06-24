package com.servertech.myboard.like.article.infra.kafka;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RedisWarmUp {
	private static final String TOPIC = "like-change";
	private final StringRedisTemplate redisTemplate;
	private final KafkaConsumer<String, LikeChange> rewindConsumer;

	@EventListener(ContextRefreshedEvent.class)
	public void onStart(ContextRefreshedEvent event) {
		//Redis 에 "article:likes:*" 같은 키가 남아 있나 확인
		log.info(">>> RedisWarmUp: ContextRefreshedEvent 발생, 워밍업 시작");

		Set<String> existingKeys = redisTemplate.keys("article:likes:*");
		if (existingKeys != null && !existingKeys.isEmpty()) {
			return; //레디스에 데이터가 있으면 rewind 불필요
		}

		//레디스에 데이터가 없으면
		List<PartitionInfo> partitions = rewindConsumer.partitionsFor(TOPIC);
		if (partitions == null || partitions.isEmpty()) {
			return;
		}

		List<TopicPartition> topicPartitions = partitions.stream()
			.map(pi -> new TopicPartition(pi.topic(), pi.partition()))
			.toList();

		rewindConsumer.assign(topicPartitions);
		rewindConsumer.seekToBeginning(topicPartitions);

		//while 루프를 통해 모든 메시지 읽어서 Redis에 복원
		while (true) {
			ConsumerRecords<String, LikeChange> records = rewindConsumer.poll(Duration.ofMillis(500));
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
		rewindConsumer.close();
	}
}
