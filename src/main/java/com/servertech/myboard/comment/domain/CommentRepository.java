package com.servertech.myboard.comment.domain;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
	Optional<Comment> findById(Long id);

	Comment save(Comment comment);

	void delete(Comment comment);

	List<Comment> findByArticleId(Long articleId);
}
