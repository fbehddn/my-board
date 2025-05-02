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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleFacade {
	private final ArticleQueryService articleQueryService;
	private final ArticleCommandService articleCommandService;
	private final AuthService authService;

	@Transactional(readOnly = true)
	public ArticleListResponse findAll() {
		List<Article> articles = articleQueryService.findAll();
		List<ArticleResponse> response = articles.stream().map(ArticleResponse::from).toList();

		return new ArticleListResponse(response);
	}

	@Transactional(readOnly = true)
	public ArticleDetailResponse find(Long id) {
		Article article = articleQueryService.find(id);
		return ArticleDetailResponse.from(article);
	}

	@Transactional
	public ArticleResponse save(CreateArticleRequest request) {
		User user = authService.getCurrentUser();
		Article article = articleCommandService.save(request, user);

		return ArticleResponse.from(article);
	}

	@Transactional()
	public void deleteArticle(Long id) {
		articleCommandService.deleteArticle(id);
	}

	@Transactional()
	public void updateArticle(Long id, UpdateArticleRequest request) {
		articleCommandService.updateArticle(id, request);
	}
}
