package com.servertech.myboard.article.application.service;

import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.global.exception.UnauthorizedException;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleCommandService {
	private final ArticleRepository articleRepository;

	@Transactional
	public Article save(CreateArticleRequest request, User user) {
		Article article = Article.create(request.title(), request.content(), user);
		return articleRepository.save(article);
	}

	@Transactional
	public void deleteArticle(Long id, User user) {
		Article article = articleRepository.find(id).orElseThrow(() -> new EntityNotFoundException("Article not found"));
		if (!article.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You do not have permission to delete this article");
		}
		articleRepository.delete(article);
	}

	@Transactional
	public void updateArticle(Long id, UpdateArticleRequest request, User user) {
		Article article = articleRepository.find(id).orElseThrow(() -> new EntityNotFoundException("Article not found"));
		if (!article.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You do not have permission to update this article");
		}
		article.update(request.title(), request.content());
	}
}
