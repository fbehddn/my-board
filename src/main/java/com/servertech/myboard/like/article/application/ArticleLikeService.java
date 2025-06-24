package com.servertech.myboard.like.article.application;

import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.like.article.application.dto.LikeChange;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
	private static final String KEY_PREFIX = "article:likes:";

	private final ArticleRepository articleRepository;
	private final StringRedisTemplate redisTemplate;
	private final ApplicationEventPublisher eventPublisher;

	@Caching(evict = {
		@CacheEvict(value = "articles::all", allEntries = true),
		@CacheEvict(value = "articles::popular", allEntries = true),
		@CacheEvict(value = "articles::detail", key = "'id:' + #articleId")
	})
	@Transactional
	public void likeArticle(Long articleId, Long userId) {
		articleRepository.find(articleId).orElseThrow(() -> new EntityNotFoundException("Article not found"));

		BoundSetOperations<String, String> ops = redisTemplate.boundSetOps(KEY_PREFIX + articleId);

		boolean added = ops.add(userId.toString()) == 1;
		if (!added) ops.remove(userId.toString());

		LikeChange change = new LikeChange(articleId, userId, added);
		eventPublisher.publishEvent(change);
	}

	public long getLikeCount(Long articleId) {
		return redisTemplate.opsForSet().size(KEY_PREFIX + articleId);
	}
}
