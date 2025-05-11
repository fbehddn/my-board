package com.servertech.myboard.comment.presentation;

import com.servertech.myboard.comment.application.CommentFacade;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.user.infra.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	private final CommentFacade commentFacade;

	@PostMapping("/articles/{articleId}/comments")
	public ResponseEntity<CommentResponse> createComment(@PathVariable Long articleId,
														 @RequestBody CreateCommentRequest request,
														 @AuthenticationPrincipal CustomUserDetails principal) {
		CommentResponse response = commentFacade.create(articleId, request, principal.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/comments/{id}")
	public ResponseEntity<Void> updateComment(@PathVariable Long id,
											  @RequestBody UpdateCommentRequest request,
											  @AuthenticationPrincipal CustomUserDetails principal) {
		commentFacade.update(id, request, principal.getId());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long id,
											  @AuthenticationPrincipal CustomUserDetails principal) {
		commentFacade.delete(id, principal.getId());
		return ResponseEntity.noContent().build();
	}
}
