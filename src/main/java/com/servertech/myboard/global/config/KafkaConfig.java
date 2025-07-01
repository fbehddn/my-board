package com.servertech.myboard.global.config;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
	private final KafkaGroupProperties props;

	@Bean
	public ConsumerFactory<String, LikeChange> warmupConsumerFactory() {
		return createFactory(props.getGroup().get("warmup").getGroupId(), "earliest");
	}

	@Bean
	public ConsumerFactory<String, LikeChange> immediateConsumerFactory() {
		return createFactory(props.getGroup().get("like").getGroupId(), "latest");
	}

	@Bean
	public KafkaConsumer<String, LikeChange> warmupConsumer(
		@Qualifier("warmupConsumerFactory") ConsumerFactory<String, LikeChange> warmupConsumerFactory
	) {
		return (KafkaConsumer<String, LikeChange>) warmupConsumerFactory.createConsumer();
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, LikeChange> immediateListenerContainerFactory(
		@Qualifier("immediateConsumerFactory") ConsumerFactory<String, LikeChange> immediateConsumerFactory
	) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, LikeChange>();
		factory.setConsumerFactory(immediateConsumerFactory);

		return factory;
	}

	private ConsumerFactory<String, LikeChange> createFactory(String groupId, String reset) {
		Map<String, Object> cfg = new HashMap<>();
		cfg.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
		cfg.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		cfg.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, reset);
		cfg.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		cfg.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		JsonDeserializer<LikeChange> valueDeserializer = new JsonDeserializer<>(LikeChange.class);
		valueDeserializer.addTrustedPackages("com.servertech.myboard");

		return new DefaultKafkaConsumerFactory<>(
			cfg,
			new StringDeserializer(),
			valueDeserializer
		);
	}
}
