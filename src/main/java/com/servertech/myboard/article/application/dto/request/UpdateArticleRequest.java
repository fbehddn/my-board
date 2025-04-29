package com.servertech.myboard.article.application.dto.request;

import lombok.Builder;

@Builder
public record UpdateArticleRequest(
	String title,
	String content
) {
}
