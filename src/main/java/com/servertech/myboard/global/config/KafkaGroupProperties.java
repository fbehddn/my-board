package com.servertech.myboard.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaGroupProperties {

	private String bootstrapServers;
	private Map<String, Group> group;

	@Getter
	@Setter
	public static class Group {
		private String groupId;
	}
}
