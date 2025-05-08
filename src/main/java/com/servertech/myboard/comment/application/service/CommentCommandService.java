package com.servertech.myboard.comment.application.service;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.global.exception.UnauthorizedException;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
	private final CommentRepository commentRepository;

	@Transactional
	public Comment create(User user, Article article, CreateCommentRequest request) {
		Comment comment = Comment.create(request.content(), user, article);
		commentRepository.save(comment);

		return comment;
	}

	@Transactional
	public void update(Long id, UpdateCommentRequest request, User user) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		if (!comment.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You do not have permission to update this comment");
		}
		comment.update(request.content());
	}

	@Transactional
	public void delete(Long id, User user) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		if (!comment.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You do not have permission to delete this comment");
		}
	}
}
