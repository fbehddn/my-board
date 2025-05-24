package com.servertech.myboard.like.article;

public record LikeChange(Long articleId, Long userId, boolean added) {
}