package com.servertech.myboard.like.article.application;

import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.application.event.LikeEventOutboxService;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import com.servertech.myboard.like.article.domain.LikeEventOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
	private static final String KEY_PREFIX = "article:likes:";

	private final ArticleRepository articleRepository;
		private final LikeEventOutboxService outboxService;
	private final ApplicationEventPublisher eventPublisher;
	private final ArticleLikeRepository articleLikeRepository;
	private final StringRedisTemplate redisTemplate;

	@Caching(evict = {
		@CacheEvict(value = "articles::all", allEntries = true),
		@CacheEvict(value = "articles::popular", allEntries = true),
		@CacheEvict(value = "articles::detail", key = "'id:' + #articleId")
	})
	@Transactional
	public void likeArticle(Long articleId, Long userId) {
		articleRepository.find(articleId).orElseThrow(() -> new EntityNotFoundException("Article not found"));
		boolean alreadyLiked = articleLikeRepository.existsByArticleIdAndUserId(articleId, userId);
		LikeChange likeChange = new LikeChange(articleId, userId, !alreadyLiked);

		LikeEventOutbox outboxEvent = outboxService.saveLikeEvent(likeChange);
		eventPublisher.publishEvent(outboxEvent);
	}

	public long getLikeCount(Long articleId) {
		Long count = redisTemplate.opsForSet().size(KEY_PREFIX + articleId);
		return count != null ? count : 0;
	}
}
