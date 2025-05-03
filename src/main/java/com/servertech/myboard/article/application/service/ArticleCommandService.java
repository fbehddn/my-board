package com.servertech.myboard.article.application.service;

import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommandService {
	private final ArticleRepository articleRepository;

	public Article save(CreateArticleRequest request, User user) {
		Article article = Article.create(request.title(), request.content(), user);
		return articleRepository.save(article);
	}

	public void deleteArticle(Long id) {
		Article article = articleRepository.find(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
		articleRepository.delete(article);
	}

	public void updateArticle(Long id, UpdateArticleRequest request) {
		Article article = articleRepository.find(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
		article.update(request.title(), request.content());
	}
}
