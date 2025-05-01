package com.servertech.myboard.article.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateArticleRequest(
	@Schema(description = "게시글 제목", example = "LG는 언제까지 연패할까요?")
	String title,

	@Schema(description = "게시글 내용", example = "이러다가 시즌 꼴등하겠습니다 ^^")
	String content,

	@Schema(description = "작성자", example = "Cole Palmer")
	String author
) {
}
