package com.servertech.myboard.article.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private String author;

	public static Article create(String title, String content, String author) {
		return Article.builder()
			.title(title)
			.content(content)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.author(author)
			.build();
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
		this.updatedAt = LocalDateTime.now();
	}
}
