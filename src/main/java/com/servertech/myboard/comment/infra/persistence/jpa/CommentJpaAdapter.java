package com.servertech.myboard.comment.infra.persistence.jpa;

import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentRepository {
	private final CommentJpaRepository commentJpaRepository;

	@Override
	public Optional<Comment> findById(Long id) {
		return commentJpaRepository.findById(id);
	}

	@Override
	public Comment save(Comment comment) {
		return commentJpaRepository.save(comment);
	}

	@Override
	public void delete(Long id) {
		commentJpaRepository.deleteById(id);
	}

	@Override
	public List<Comment> findByArticleId(Long articleId) {
		return commentJpaRepository.findByArticleId(articleId);
	}
}
