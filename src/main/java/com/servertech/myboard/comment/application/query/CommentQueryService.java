package com.servertech.myboard.comment.application.query;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
	private final CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public List<Comment> findByArticleId(Long articleId) {
		return commentRepository.findByArticleId(articleId);
	}
}
