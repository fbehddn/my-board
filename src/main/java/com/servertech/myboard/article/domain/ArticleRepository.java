package com.servertech.myboard.article.domain;

import java.util.List;

public interface ArticleRepository {
	List<Article> findAll();

	Article find(long id);

	Article save(Article article);

	void delete(Article article);
}
