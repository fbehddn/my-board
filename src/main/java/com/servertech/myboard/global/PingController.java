package com.servertech.myboard.global;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PingController {
	private final KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping("/ping")
	public String ping() {
		kafkaTemplate.send("quick-test", "ping-" + System.currentTimeMillis());
		return "sent";
	}

	@KafkaListener(topics = "quick-test", groupId = "my-group")
	public void listen(String message) {
		System.out.println("### received: " + message);
	}
}
