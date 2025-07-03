package com.servertech.myboard.like.article.domain;

import com.servertech.myboard.like.article.application.event.LikeChangeEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeEventOutboxRepository extends JpaRepository<LikeEventOutbox, Long> {
}
