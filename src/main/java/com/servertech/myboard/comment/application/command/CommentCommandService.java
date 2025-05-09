package com.servertech.myboard.comment.application.command;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.global.exception.UnauthorizedException;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentCommandService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;

	@Transactional
	public Comment create(Long userId, Long articleId, CreateCommentRequest request) {
		User user = userRepository.getReferenceById(userId);
		Article article = articleRepository.getReferenceById(articleId);
		Comment comment = Comment.create(request.content(), user, article);
		return	commentRepository.save(comment);
	}

	@Transactional
	public void update(Long id, UpdateCommentRequest request, Long userId) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		if (!comment.getUser().getId().equals(userId)) {
			throw new UnauthorizedException("You do not have permission to update this comment");
		}
		comment.update(request.content());
	}

	@Transactional
	public void delete(Long id, Long userId) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
		if (!comment.getUser().getId().equals(userId)) {
			throw new UnauthorizedException("You do not have permission to delete this comment");
		}
		commentRepository.delete(comment);
	}
}
