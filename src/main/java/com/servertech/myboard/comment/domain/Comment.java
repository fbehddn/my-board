package com.servertech.myboard.comment.domain;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.common.BaseTimeEntity;
import com.servertech.myboard.user.domain.User;
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
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	public static Comment create(String content, User user, Article article) {
		return Comment.builder()
			.content(content)
			.author(user.getUsername())
			.user(user)
			.article(article)
			.build();
	}

	public void update(String content) {
		this.content = content;
	}
}
