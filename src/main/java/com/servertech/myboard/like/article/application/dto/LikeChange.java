package com.servertech.myboard.like.article.application.dto;

public record LikeChange(Long articleId, Long userId, boolean added) {
}