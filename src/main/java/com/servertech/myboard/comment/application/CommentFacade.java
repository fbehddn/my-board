package com.servertech.myboard.comment.application;

import com.servertech.myboard.article.application.service.ArticleQueryService;
import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.auth.application.service.AuthService;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentDetailResponse;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.comment.application.service.CommentCommandService;
import com.servertech.myboard.comment.application.service.CommentQueryService;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.like.comment.service.CommentLikeQueryService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {
	private final CommentQueryService commentQueryService;
	private final CommentCommandService commentCommandService;
	private final ArticleQueryService articleQueryService;
	private final AuthService authService;
	private final CommentLikeQueryService commentLikeQueryService;

	public CommentListResponse findByArticleId(Long articleId) {
		List<Comment> comments = commentQueryService.findByArticleId(articleId);
		List<CommentDetailResponse> responses = comments.stream().map(comment -> {
			Long likeCount = commentLikeQueryService.countByCommentId(comment.getId());
			return CommentDetailResponse.from(comment, likeCount);
		}).toList();

		return CommentListResponse.from(responses);
	}

	public CommentResponse create(Long articleId, CreateCommentRequest request) {
		User user = authService.currentUser();
		Article article = articleQueryService.find(articleId);
		Comment comment = commentCommandService.create(user, article, request);

		return CommentResponse.from(comment);
	}

	public void update(Long id, UpdateCommentRequest request) {
		User user = authService.currentUser();
		commentCommandService.update(id, request,user);
	}

	public void delete(Long id) {
		User user = authService.currentUser();
		commentCommandService.delete(id, user);
	}
}
