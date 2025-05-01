package com.servertech.myboard.comment.infra;

import com.servertech.myboard.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
