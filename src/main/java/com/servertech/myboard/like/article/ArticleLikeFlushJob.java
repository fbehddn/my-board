package com.servertech.myboard.like.article;

import com.servertech.myboard.like.article.domain.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticleLikeFlushJob {
	private final LikeChangeQueue likeChangeQueue;
	private final ArticleLikeRepository articleLikeRepository;

	@Scheduled(fixedDelayString = "${like.flush.interval:60000}")
	@Transactional
	public void flush() {
		for (LikeChange lc : likeChangeQueue.drain()) {
			if (lc.added()) {
				articleLikeRepository.insertIgnore(lc.articleId(), lc.userId());
			} else {
				articleLikeRepository.deleteByArticleIdAndUserId(lc.articleId(), lc.userId());
			}
		}
	}
}
