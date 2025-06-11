package com.servertech.myboard.like.article.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeEventOutboxRepository extends JpaRepository<LikeEventOutbox, Long> {
	List<LikeEventOutbox> findByStatus(EventStatus eventStatus);
}
