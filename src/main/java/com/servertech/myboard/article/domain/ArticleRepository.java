package com.servertech.myboard.article.domain;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
	List<Article> findAll();

	Optional<Article> find(long id);

	Article save(Article article);

	void delete(Article article);
}
