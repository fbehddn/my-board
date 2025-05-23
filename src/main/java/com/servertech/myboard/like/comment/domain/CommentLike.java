package com.servertech.myboard.like.comment.domain;

import com.servertech.myboard.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "comment_likes",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_comment_like_comment_user",
		columnNames = {"comment_id", "user_id"}
	)
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLike {
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
