package com.servertech.myboard.like.domain;

import com.servertech.myboard.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long targetId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public static Like create(Long targetId, User user) {
		return Like.builder()
			.targetId(targetId)
			.user(user)
			.build();
	}
}
