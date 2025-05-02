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
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {
	private final CommentCommandService commentCommandService;
	private final ArticleQueryService articleQueryService;
	private final AuthService authService;

	public CommentListResponse findByArticleId(Long articleId) {
		Article article = articleQueryService.find(articleId);
		List<CommentDetailResponse> comments = article.getComments().stream().map(CommentDetailResponse::from).toList();

		return CommentListResponse.from(comments);
	}

	public CommentResponse create(Long articleId, CreateCommentRequest request) {
		User user = authService.getCurrentUser();
		Article article = articleQueryService.find(articleId);
		Comment comment = commentCommandService.create(user, article, request);

		return CommentResponse.from(comment);
	}

	public void update(Long id, UpdateCommentRequest request) {
		commentCommandService.update(id, request);
	}

	public void delete(Long id) {
		commentCommandService.delete(id);
	}
}
