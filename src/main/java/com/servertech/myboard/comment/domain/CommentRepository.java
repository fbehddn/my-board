package com.servertech.myboard.comment.domain;

import java.util.List;

public interface CommentRepository {
	List<Comment> findAll();

	Comment findById(Long id);

	Comment save(Comment comment);

	void delete(Long id);
}
