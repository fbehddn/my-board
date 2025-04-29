package com.servertech.myboard.article.infra;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {
	private final ArticleJpaRepository articleJpaRepository;

	@Override
	public List<Article> findAll() {
		return articleJpaRepository.findAll();
	}

	@Override
	public Article find(long id) {
		return articleJpaRepository.findById(id).orElse(null);
	}

	@Override
	public Article save(Article article) {
		return articleJpaRepository.save(article);
	}

	@Override
	public void delete(Article article) {
		articleJpaRepository.delete(article);
	}
}
