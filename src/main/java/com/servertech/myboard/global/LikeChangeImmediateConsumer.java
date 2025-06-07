package com.servertech.myboard.global;

import com.servertech.myboard.like.article.LikeChange;
import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeChangeImmediateConsumer {
	private final ArticleLikeRepository articleLikeRepository;

	@KafkaListener(
		topics = "like-change",
		groupId = "like-immediate-group"
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
