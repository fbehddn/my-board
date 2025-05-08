package com.servertech.myboard.like.comment.domain;

import com.servertech.myboard.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLike extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "comment_id", nullable = false)
	private Long commentId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	public static CommentLike create(Long commentId, Long userId) {
		return CommentLike.builder()
			.commentId(commentId)
			.userId(userId)
			.build();
	}
}
