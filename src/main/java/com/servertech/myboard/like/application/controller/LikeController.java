package com.servertech.myboard.like.application.controller;

import com.servertech.myboard.like.application.LikeFacade;
import com.servertech.myboard.user.domain.User;
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
	private final LikeFacade likeFacade;

	@PostMapping("/articles/{articleId}")
	public ResponseEntity<Void> likeArticle(@PathVariable Long articleId,
											@AuthenticationPrincipal User user) {
		likeFacade.likeArticle(articleId, user.getUsername());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/comments/{commentId}")
	public ResponseEntity<Void> likeComment(@PathVariable Long commentId,
											@AuthenticationPrincipal User user) {
		likeFacade.likeComment(commentId, user.getUsername());
		return ResponseEntity.ok().build();
	}
}
