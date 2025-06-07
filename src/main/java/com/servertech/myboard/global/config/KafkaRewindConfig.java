package com.servertech.myboard.global.config;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaRewindConfig {

	@Value("${kafka.rewind.group-id}")
	private String warmupGroupId;

	@Bean
	public ConsumerFactory<String, LikeChange> rewindConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, warmupGroupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		JsonDeserializer<LikeChange> valueDeserializer = new JsonDeserializer<>(LikeChange.class);
		valueDeserializer.addTrustedPackages("com.servertech.myboard.like.article");

		return new DefaultKafkaConsumerFactory<>(
			props,
			new StringDeserializer(),
			valueDeserializer
		);
	}

	@Bean
	public KafkaConsumer<String, LikeChange> rewindConsumer(
		ConsumerFactory<String, LikeChange> rewindConsumerFactory
	) {
		// 동적으로 새로운 컨슈머 인스턴스를 만들어 주입
		return (KafkaConsumer<String, LikeChange>) rewindConsumerFactory.createConsumer();
	}
}
