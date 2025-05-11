package com.servertech.myboard.comment.infra.persistence.jpa;

import com.servertech.myboard.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByArticleId(Long articleId);
}
