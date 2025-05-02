package com.servertech.myboard.article.application.controller;

import com.servertech.myboard.article.application.ArticleFacade;
import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.application.dto.response.ArticleDetailResponse;
import com.servertech.myboard.article.application.dto.response.ArticleListResponse;
import com.servertech.myboard.article.application.dto.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
	private final ArticleFacade articleFacade;

	@GetMapping
	public ArticleListResponse getArticles() {
		return articleFacade.findAll();
	}

	@PostMapping
	public ArticleResponse createArticle(@RequestBody CreateArticleRequest request) {
		return articleFacade.save(request);
	}

	@GetMapping("/{id}")
	public ArticleDetailResponse getArticle(@PathVariable Long id) {
		return articleFacade.find(id);
	}

	@DeleteMapping("/{id}")
	public void deleteArticle(@PathVariable Long id) {
		articleFacade.deleteArticle(id);
	}

	@PatchMapping("/{id}")
	public void updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
		articleFacade.updateArticle(id, request);
	}
}
