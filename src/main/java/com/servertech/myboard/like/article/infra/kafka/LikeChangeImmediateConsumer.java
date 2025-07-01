package com.servertech.myboard.like.article.infra.kafka;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LikeChangeImmediateConsumer {
	private final ArticleLikeRepository articleLikeRepository;

	@KafkaListener(
		topics = "like-change",
		containerFactory = "immediateListenerContainerFactory"
	)
	@Transactional
	public void listen(LikeChange likeChange) {
		Long articleId = likeChange.articleId();
		Long userId = likeChange.userId();

		if (likeChange.added()) {
			articleLikeRepository.insertIgnore(articleId, userId);
		} else {
			articleLikeRepository.deleteByArticleIdAndUserId(articleId, userId);
		}
	}
}
