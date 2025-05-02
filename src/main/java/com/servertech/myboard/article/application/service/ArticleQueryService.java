package com.servertech.myboard.article.application.service;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleQueryService {
	private final ArticleRepository articleRepository;

	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public Article find(Long id) {
		return articleRepository.find(id);
	}
}
