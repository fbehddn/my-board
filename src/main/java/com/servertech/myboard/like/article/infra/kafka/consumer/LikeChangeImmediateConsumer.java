package com.servertech.myboard.like.article.infra.kafka.consumer;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.domain.ArticleLike;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeChangeImmediateConsumer {
	private static final String KEY_PREFIX = "article:likes:";
	private final ArticleLikeRepository articleLikeRepository;
	private final StringRedisTemplate redisTemplate;

	@KafkaListener(
		topics = "like-change",
		containerFactory = "immediateListenerContainerFactory"
	)
	@Transactional
	public void listen(LikeChange likeChange) {
		Long articleId = likeChange.articleId();
		Long userId = likeChange.userId();
		if (likeChange.added()) {
			ArticleLike newLike = ArticleLike.create(articleId, userId);
			try {
				articleLikeRepository.save(newLike);
			} catch (DataIntegrityViolationException e) {
				log.warn("Like already exists, ignoring. Article: {}, User: {}", likeChange.articleId(), likeChange.userId());
			}
		} else {
			articleLikeRepository.deleteByArticleIdAndUserId(articleId, userId);
		}

		BoundSetOperations<String, String> ops = redisTemplate.boundSetOps(KEY_PREFIX + articleId);
		if (likeChange.added()) {
			ops.add(userId.toString());
		} else {
			ops.remove(userId.toString());
		}
	}
}
