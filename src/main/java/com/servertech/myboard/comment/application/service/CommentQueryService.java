package com.servertech.myboard.comment.application.service;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
	private final CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public Comment find(Long id) {
		return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다: id=" + id));
	}

	@Transactional(readOnly = true)
	public List<Comment> findByArticleId(Long articleId) {
		return commentRepository.findByArticleId(articleId);
	}
}
