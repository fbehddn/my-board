package com.servertech.myboard.like.presentation;

import com.servertech.myboard.like.article.application.ArticleLikeService;
import com.servertech.myboard.like.comment.application.command.CommentLikeCommandService;
import com.servertech.myboard.user.infra.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
	private final ArticleLikeService articleLikeService;
	private final CommentLikeCommandService commentLikeCommandService;

	@PostMapping("/articles/{articleId}")
	public ResponseEntity<Void> likeArticle(@PathVariable Long articleId,
											@AuthenticationPrincipal CustomUserDetails principal) {
		articleLikeService.likeArticle(articleId, principal.getId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/comments/{commentId}")
	public ResponseEntity<Void> likeComment(@PathVariable Long commentId,
											@AuthenticationPrincipal CustomUserDetails principal) {
		commentLikeCommandService.likeComment(commentId, principal.getId());
		return ResponseEntity.ok().build();
	}
}
