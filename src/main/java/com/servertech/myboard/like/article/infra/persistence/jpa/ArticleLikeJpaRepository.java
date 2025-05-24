package com.servertech.myboard.like.article.infra.persistence.jpa;

import com.servertech.myboard.like.article.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleLikeJpaRepository extends JpaRepository<ArticleLike, Long> {
	Long countByArticleId(Long articleId);

	boolean existsByArticleIdAndUserId(Long articleId, Long userId);

	void deleteByArticleIdAndUserId(Long articleId, Long userId);

	@Modifying
	@Query(value = """
		INSERT IGNORE INTO article_likes (article_id, user_id)
		VALUES (:articleId, :userId)
		""", nativeQuery = true)
	void insertIgnore(Long articleId, Long userId);
}
