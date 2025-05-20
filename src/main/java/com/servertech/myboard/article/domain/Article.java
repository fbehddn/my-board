package com.servertech.myboard.article.domain;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.global.BaseTimeEntity;
import com.servertech.myboard.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	@Builder.Default
	@Column(nullable = false)
	private long likeCount = 0L;

	public static Article create(String title, String content, User user) {
		return Article.builder()
			.title(title)
			.content(content)
			.author(user.getUsername())
			.user(user)
			.likeCount(0L)
			.build();
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void like() {
		this.likeCount++;
	}

	public void unlike() {
		if (this.likeCount > 0) this.likeCount--;
	}
}
