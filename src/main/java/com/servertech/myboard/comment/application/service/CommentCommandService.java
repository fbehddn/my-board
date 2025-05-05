package com.servertech.myboard.comment.application.service;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
	private final CommentRepository commentRepository;

	public Comment create(User user, Article article, CreateCommentRequest request) {
		Comment comment = Comment.create(request.content(), user, article);
		commentRepository.save(comment);

		return comment;
	}

	@Transactional
	public void update(Long id, UpdateCommentRequest request) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		comment.update(request.content());
	}

	public void delete(Long id) {
		commentRepository.delete(id);
	}
}
