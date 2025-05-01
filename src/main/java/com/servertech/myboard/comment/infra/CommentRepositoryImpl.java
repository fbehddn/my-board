package com.servertech.myboard.comment.infra;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
	private final CommentJpaRepository commentJpaRepository;

	@Override
	public List<Comment> findAll() {
		return commentJpaRepository.findAll();
	}

	@Override
	public Comment findById(Long id) {
		return commentJpaRepository.findById(id).orElse(null);
	}

	@Override
	public Comment save(Comment comment) {
		return commentJpaRepository.save(comment);
	}

	@Override
	public void delete(Long id) {
		commentJpaRepository.deleteById(id);
	}
}
