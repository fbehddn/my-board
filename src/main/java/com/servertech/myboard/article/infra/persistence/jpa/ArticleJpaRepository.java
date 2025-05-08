package com.servertech.myboard.article.infra.persistence.jpa;

import com.servertech.myboard.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
	Page<Article> findAllByOrderByLikeCountDesc(Pageable pageable);
}
