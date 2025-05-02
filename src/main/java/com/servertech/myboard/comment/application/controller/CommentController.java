package com.servertech.myboard.comment.application.controller;

import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.comment.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	private final CommentService commentService;

	@GetMapping("/articles/{articleId}/comments")
	public CommentListResponse getComments(@PathVariable Long articleId) {
		return commentService.findByArticleId(articleId);
	}

	@PostMapping("/articles/{articleId}/comments")
	public CommentResponse createComment(@PathVariable Long articleId,
										 @RequestBody CreateCommentRequest request) {
		return commentService.create(articleId, request);
	}

	@PatchMapping("/comments/{id}")
	public void updateComment(@PathVariable Long id,
							  @RequestBody UpdateCommentRequest request) {
		commentService.update(id, request);
	}

	@DeleteMapping("/comments/{id}")
	public void deleteComment(@PathVariable Long id) {
		commentService.delete(id);
	}
}
