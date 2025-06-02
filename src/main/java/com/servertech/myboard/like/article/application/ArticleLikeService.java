package com.servertech.myboard.like.article.application;

import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.like.article.LikeChange;
import com.servertech.myboard.like.article.LikeChangeQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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
	private final LikeChangeQueue likeChangeQueue;

	@Caching(evict = {
		@CacheEvict(value = "articles::all", allEntries = true),
		@CacheEvict(value = "articles::popular", allEntries = true),
		@CacheEvict(value = "articles::detail", key = "'id:' + #articleId")
	})
	@Transactional(readOnly = true)
	public void likeArticle(Long articleId, Long userId) {
		articleRepository.find(articleId).orElseThrow(() -> new EntityNotFoundException("Article not found"));

		String key = KEY_PREFIX + articleId;
		BoundSetOperations<String, String> ops = redisTemplate.boundSetOps(key);

		boolean added = ops.add(userId.toString()) == 1;
		if (!added) {
			ops.remove(userId.toString());
		}
		likeChangeQueue.enqueue(new LikeChange(articleId, userId, added));
	}

	public long getLikeCount(Long articleId) {
		String key = KEY_PREFIX + articleId;
		return redisTemplate.opsForSet().size(key);
	}
}
