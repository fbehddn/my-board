package com.servertech.myboard.auth.infra.persistence.redis;

import com.servertech.myboard.auth.domain.RefreshToken;
import com.servertech.myboard.auth.domain.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenStore implements RefreshTokenStore {
	private final StringRedisTemplate stringRedisTemplate;
	private static final String KEY_PREFIX = "rt:";

	@Override
	public void save(RefreshToken rt) {
		stringRedisTemplate.opsForValue().set(KEY_PREFIX + rt.email(), rt.value(), rt.ttl());
	}

	@Override
	public Optional<String> find(String email) {
		return Optional.ofNullable(stringRedisTemplate.opsForValue().get(KEY_PREFIX + email));
	}

	@Override
	public void delete(String email) {
		stringRedisTemplate.delete(KEY_PREFIX + email);
	}
}
