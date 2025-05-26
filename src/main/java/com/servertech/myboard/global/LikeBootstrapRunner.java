package com.servertech.myboard.global;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeBootstrapRunner implements ApplicationRunner {
	@Value("${like.bootstrap.enabled:false}")
	private boolean bootstrapEnabled;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(!bootstrapEnabled) return;

	}
}
