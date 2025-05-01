package com.servertech.myboard.comment.application.controller;

import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentDetailResponse;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.comment.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
	private final CommentService commentService;

	@GetMapping
	public CommentListResponse getAllComments() {
		return commentService.findAll();
	}

	@GetMapping("/{id}")
	public CommentDetailResponse getComments(@PathVariable Long id) {
		return commentService.find(id);
	}

	@PostMapping
	public CommentResponse createComment(@RequestBody CreateCommentRequest request) {
		return commentService.create(request);
	}

	@PatchMapping("/{id}")
	public void updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest request) {
		commentService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public void deleteComment(@PathVariable Long id) {
		commentService.delete(id);
	}
}
