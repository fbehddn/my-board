package com.servertech.myboard.article.application.dto.request;

import lombok.Builder;

@Builder
public record CreateArticleRequest(
	String title,
	String content,
	String author
) {
}
