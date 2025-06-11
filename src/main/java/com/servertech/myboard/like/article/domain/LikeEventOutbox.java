package com.servertech.myboard.like.article.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_event_outbox")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeEventOutbox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long articleId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private boolean added;

	public static LikeEventOutbox create(Long articleId, Long userId, boolean added) {
		return LikeEventOutbox.builder()
			.articleId(articleId)
			.userId(userId)
			.added(added)
			.build();
	}
}
