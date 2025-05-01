package com.servertech.myboard.comment.application.service;

import com.servertech.myboard.comment.application.dto.request.CreateCommentRequest;
import com.servertech.myboard.comment.application.dto.request.UpdateCommentRequest;
import com.servertech.myboard.comment.application.dto.response.CommentDetailResponse;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.comment.application.dto.response.CommentResponse;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	public CommentListResponse findAll() {
		List<Comment> comments = commentRepository.findAll();
		List<CommentDetailResponse> responses = comments.stream().map(CommentDetailResponse::from).toList();

		return CommentListResponse.from(responses);
	}

	public CommentDetailResponse find(Long id) {
		Comment comment = commentRepository.findById(id);
		return CommentDetailResponse.from(comment);
	}

	public CommentResponse create(CreateCommentRequest request) {
		Comment comment = Comment.create(request.content());
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
