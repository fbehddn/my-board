package com.servertech.myboard.article.domain;

import com.servertech.myboard.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public static Article create(String title, String content, String author) {
		return Article.builder()
			.title(title)
			.content(content)
			.author(author)
			.build();
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
