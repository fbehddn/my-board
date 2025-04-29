package com.servertech.myboard.article.application.controller;

import com.servertech.myboard.article.application.service.ArticleService;
import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.application.dto.response.ArticleDetailResponse;
import com.servertech.myboard.article.application.dto.response.ArticleListResponse;
import com.servertech.myboard.article.application.dto.response.ArticleResponse;
import com.servertech.myboard.article.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
	private final ArticleService articleService;

	@GetMapping
	public ArticleListResponse getArticles() {
		return articleService.findAll();
	}

	@PostMapping
	public ArticleResponse createArticle(@RequestBody CreateArticleRequest request) {
		Article savedArticle = articleService.save(request);
		return ArticleResponse.from(savedArticle);
	}

	@GetMapping("/{id}")
	public ArticleDetailResponse getArticle(@PathVariable Long id) {
		return articleService.find(id);
	}

	@DeleteMapping("/{id}")
	public void deleteArticle(@PathVariable Long id) {
		articleService.deleteArticle(id);
	}

	@PatchMapping("/{id}")
	public void updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
		articleService.updateArticle(id, request);
	}
}
