package com.servertech.myboard.like.article.application.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.servertech.myboard.like.article.application.dto.LikeChange;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LikeChangeEvent(
	//LikeChange 이벤트를 LikeChangeEvent 로 감싸서 이벤트 발행
	Long articleId,
	Long userId,
	boolean added,

	//좋아요 트랜잭션이 발생한 시점
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	LocalDateTime createdAt
) {
	public static LikeChangeEvent create(LikeChange likeChange) {
		return LikeChangeEvent.builder()
			.articleId(likeChange.articleId())
			.userId(likeChange.userId())
			.added(likeChange.added())
			.createdAt(LocalDateTime.now())
			.build();
	}
}
