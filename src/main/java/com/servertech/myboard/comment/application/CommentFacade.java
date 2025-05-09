package com.servertech.myboard.comment.application;

import com.servertech.myboard.comment.application.command.CommentCommandService;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentDetailResponse;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.comment.application.query.CommentQueryService;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.like.comment.application.query.CommentLikeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {
	private final CommentQueryService commentQueryService;
	private final CommentCommandService commentCommandService;
	private final CommentLikeQueryService commentLikeQueryService;

	public CommentListResponse findByArticleId(Long articleId) {
		List<Comment> comments = commentQueryService.findByArticleId(articleId);
		List<CommentDetailResponse> responses = comments.stream().map(comment -> {
			Long likeCount = commentLikeQueryService.countByCommentId(comment.getId());
			return CommentDetailResponse.from(comment, likeCount);
		}).toList();

		return CommentListResponse.from(responses);
	}

	public CommentResponse create(Long articleId, CreateCommentRequest request, Long userId) {
		Comment comment = commentCommandService.create(userId, articleId, request);
		return CommentResponse.from(comment);
	}

	public void update(Long id, UpdateCommentRequest request, Long userId) {
		commentCommandService.update(id, request, userId);
	}

	public void delete(Long id, Long userId) {
		commentCommandService.delete(id, userId);
	}
}
