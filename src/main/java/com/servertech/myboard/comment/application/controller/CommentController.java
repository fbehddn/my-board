package com.servertech.myboard.comment.application.controller;

import com.servertech.myboard.comment.application.CommentFacade;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	private final CommentFacade commentFacade;

	@GetMapping("/articles/{articleId}/comments")
	public CommentListResponse getComments(@PathVariable Long articleId) {
		return commentFacade.findByArticleId(articleId);
	}

	@PostMapping("/articles/{articleId}/comments")
	public CommentResponse createComment(@PathVariable Long articleId,
										 @RequestBody CreateCommentRequest request) {
		return commentFacade.create(articleId, request);
	}

	@PatchMapping("/comments/{id}")
	public void updateComment(@PathVariable Long id,
							  @RequestBody UpdateCommentRequest request) {
		commentFacade.update(id, request);
	}

	@DeleteMapping("/comments/{id}")
	public void deleteComment(@PathVariable Long id) {
		commentFacade.delete(id);
	}
}
