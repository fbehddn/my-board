package com.servertech.myboard.article.infra;

import com.servertech.myboard.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
}
