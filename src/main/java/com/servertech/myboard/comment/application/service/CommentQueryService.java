package com.servertech.myboard.comment.application.service;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
	private final CommentRepository commentRepository;

	public Comment find(Long id) {
		return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("댓글 찾을 수 없습니다: id=" + id));
	}
}
