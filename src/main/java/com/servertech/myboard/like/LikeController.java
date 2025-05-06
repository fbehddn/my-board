package com.servertech.myboard.like;

import com.servertech.myboard.like.article.application.ArticleLikeFacade;
import com.servertech.myboard.like.comment.application.CommentLikeFacade;
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
	private final ArticleLikeFacade articleLikeFacade;
	private final CommentLikeFacade commentLikeFacade;

	@PostMapping("/articles/{articleId}")
	public ResponseEntity<Void> likeArticle(@PathVariable Long articleId,
											@AuthenticationPrincipal User user) {
		articleLikeFacade.likeArticle(articleId, user.getUsername());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/comments/{commentId}")
	public ResponseEntity<Void> likeComment(@PathVariable Long commentId,
											@AuthenticationPrincipal User user) {
		commentLikeFacade.likeComment(commentId, user.getUsername());
		return ResponseEntity.ok().build();
	}
}
