package com.servertech.myboard.article.infra.persistence.jpa;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleJpaAdapter implements ArticleRepository {
	private final ArticleJpaRepository articleJpaRepository;

	@Override
	public Page<Article> findAll(Pageable pageable) {
		return articleJpaRepository.findAll(pageable);
	}

	@Override
	public Page<Article> findAllByOrderByLikeCountDesc(Pageable pageable) {
		return articleJpaRepository.findAllByOrderByLikeCountDesc(pageable);
	}

	@Override
	public Optional<Article> find(long id) {
		return articleJpaRepository.findById(id);
	}

	@Override
	public Article getReferenceById(long id) {
		return articleJpaRepository.getReferenceById(id);
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
