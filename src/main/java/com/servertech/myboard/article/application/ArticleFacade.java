package com.servertech.myboard.article.application;

import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.application.dto.response.ArticleDetailResponse;
import com.servertech.myboard.article.application.dto.response.ArticleListResponse;
import com.servertech.myboard.article.application.dto.response.ArticleResponse;
import com.servertech.myboard.article.application.service.ArticleCommandService;
import com.servertech.myboard.article.application.service.ArticleQueryService;
import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.auth.application.service.AuthService;
import com.servertech.myboard.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleFacade {
	private final ArticleQueryService articleQueryService;
	private final ArticleCommandService articleCommandService;
	private final AuthService authService;

	@Cacheable(value = "articles", key = "'page:' + #pageable.pageNumber")
	public ArticleListResponse findAll(Pageable pageable) {
		Page<Article> articles = articleQueryService.findAll(pageable);
		List<ArticleResponse> response = articles.stream()
			.map(ArticleResponse::from)
			.toList();

		return ArticleListResponse.from(response);
	}

	@Cacheable(value = "articles", key = "'popular&page:' + #pageable.pageNumber")
	public ArticleListResponse findPopular(Pageable pageable) {
		Page<Article> articles = articleQueryService.findAllByPopular(pageable);
		List<ArticleResponse> response = articles.stream()
			.map(ArticleResponse::from)
			.toList();

		return ArticleListResponse.from(response);
	}

	public ArticleDetailResponse find(Long id) {
		Article article = articleQueryService.find(id);

		return ArticleDetailResponse.from(article);
	}

	@CacheEvict(value = "articles", allEntries = true)
	public ArticleResponse save(CreateArticleRequest request) {
		User user = authService.currentUser();
		Article article = articleCommandService.save(request, user);

		return ArticleResponse.from(article);
	}

	@CacheEvict(value = "articles", allEntries = true)
	public void deleteArticle(Long id) {
		User user = authService.currentUser();
		articleCommandService.deleteArticle(id,user);
	}

	@CacheEvict(value = "articles", allEntries = true)
	public void updateArticle(Long id, UpdateArticleRequest request) {
		User user = authService.currentUser();
		articleCommandService.updateArticle(id, request, user);
	}
}
