package com.servertech.myboard.comment.presentation;

import com.servertech.myboard.comment.application.CommentFacade;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	private final CommentFacade commentFacade;

	@PostMapping("/articles/{articleId}/comments")
	public ResponseEntity<CommentResponse> createComment(@PathVariable Long articleId,
														 @RequestBody CreateCommentRequest request) {
		CommentResponse response = commentFacade.create(articleId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/comments/{id}")
	public ResponseEntity<Void> updateComment(@PathVariable Long id,
											  @RequestBody UpdateCommentRequest request) {
		commentFacade.update(id, request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
		commentFacade.delete(id);
		return ResponseEntity.noContent().build();
	}
}
