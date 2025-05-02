package com.servertech.myboard.comment.application.service;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentDetailResponse;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;

	public CommentListResponse findAll() {
		List<Comment> comments = commentRepository.findAll();
		List<CommentDetailResponse> responses = comments.stream().map(CommentDetailResponse::from).toList();

		return CommentListResponse.from(responses);
	}

	public CommentDetailResponse find(Long id) {
		Comment comment = commentRepository.findById(id);
		return CommentDetailResponse.from(comment);
	}

	public CommentResponse create(Long articleId, CreateCommentRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof UserDetails userDetails)) {
			throw new IllegalStateException("User is not authenticated");
		}
		User user = userRepository.findByEmail(userDetails.getUsername())
			.orElseThrow(() -> new IllegalStateException("User not found"));

		Article article = articleRepository.find(articleId);
		Comment comment = Comment.create(request.content(), user, article);
		commentRepository.save(comment);

		return CommentResponse.from(comment);
	}

	@Transactional
	public void update(Long id, UpdateCommentRequest request) {
		Comment comment = commentRepository.findById(id);
		comment.update(request.content());
	}

	public void delete(Long id) {
		commentRepository.delete(id);
	}
}
