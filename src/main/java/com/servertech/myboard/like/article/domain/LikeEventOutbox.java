package com.servertech.myboard.like.article.domain;

import com.servertech.myboard.like.article.application.dto.LikeChange;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_event_outbox")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeEventOutbox {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean added;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static LikeEventOutbox from(LikeChange change) {
        return LikeEventOutbox.builder()
            .articleId(change.articleId())
            .userId(change.userId())
            .added(change.added())
            .createdAt(LocalDateTime.now())
            .build();
    }
}