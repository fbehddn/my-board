package com.servertech.myboard.article.application.service;

import com.servertech.myboard.article.application.dto.response.ArticleDetailResponse;
import com.servertech.myboard.article.application.dto.response.ArticleListResponse;
import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;

	public ArticleListResponse findAll() {
		List<Article> articles = articleRepository.findAll();
		List<ArticleDetailResponse> responses = articles.stream().map(ArticleDetailResponse::from).toList();

		return ArticleListResponse.from(responses);
	}

	public ArticleDetailResponse find(Long id) {
		Article article = articleRepository.find(id);
		return ArticleDetailResponse.from(article);
	}

	public Article save(CreateArticleRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof UserDetails userDetails)) {
			throw new IllegalStateException("User is not authenticated");
		}

		User user = userRepository.findByEmail(userDetails.getUsername())
			.orElseThrow(() -> new IllegalStateException("User not found"));

		Article article = Article.create(request.title(), request.content(), user);
		return articleRepository.save(article);
	}

	public void deleteArticle(Long id) {
		Article article = articleRepository.find(id);
		articleRepository.delete(article);
	}

	@Transactional
	public void updateArticle(Long id, UpdateArticleRequest request) {
		Article article = articleRepository.find(id);
		article.update(request.title(), request.content());
	}
}
