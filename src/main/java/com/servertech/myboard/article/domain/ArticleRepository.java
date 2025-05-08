package com.servertech.myboard.article.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleRepository {
	Page<Article> findAll(Pageable pageable);

	Page<Article> findAllByOrderByLikeCountDesc(Pageable pageable);

	Optional<Article> find(long id);

	Article getReferenceById(long id);

	Article save(Article article);

	void delete(Article article);
}
