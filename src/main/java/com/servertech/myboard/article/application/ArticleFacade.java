package com.servertech.myboard.article.application;

import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.application.dto.response.ArticleDetailResponse;
import com.servertech.myboard.article.application.dto.response.ArticleListResponse;
import com.servertech.myboard.article.application.dto.response.ArticleResponse;
import com.servertech.myboard.article.application.command.ArticleCommandService;
import com.servertech.myboard.article.application.query.ArticleQueryService;
import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.like.article.application.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleFacade {
	private final ArticleQueryService articleQueryService;
	private final ArticleCommandService articleCommandService;
	private final ArticleLikeService articleLikeService;

	@Cacheable(value = "articles::all", key = "'p:' + #pageable.pageNumber + ':s:' + #pageable.pageSize")
	public ArticleListResponse findAll(Pageable pageable) {
		Page<Article> articles = articleQueryService.findAll(pageable);
		List<ArticleResponse> response = articles.stream()
			.map(ArticleResponse::from)
			.toList();

		return ArticleListResponse.from(response);
	}

//	@Cacheable(value = "articles::popular", key = "'p:' + #pageable.pageNumber + ':s:' + #pageable.pageSize")
//	public ArticleListResponse findPopular(Pageable pageable) {
//		Page<Article> articles = articleQueryService.findAllByPopular(pageable);
//		List<ArticleResponse> response = articles.stream()
//			.map(ArticleResponse::from)
//			.toList();
//
//		return ArticleListResponse.from(response);
//	}

	@Cacheable(value = "articles", key = "'id:' + #id")
	public ArticleDetailResponse find(Long id) {
		Article article = articleQueryService.find(id);
		long likeCount = articleLikeService.getLikeCount(id);

		return ArticleDetailResponse.from(article, likeCount);
	}

	@Caching(evict = {
		@CacheEvict(value = "articles::all", allEntries = true),
		@CacheEvict(value = "articles::popular", allEntries = true)
	})
	public ArticleResponse save(CreateArticleRequest request, Long userId) {
		Article article = articleCommandService.save(request, userId);

		return ArticleResponse.from(article);
	}

	@Caching(evict = {
		@CacheEvict(value = "articles::all", allEntries = true),
		@CacheEvict(value = "articles::popular", allEntries = true)
	})
	public void deleteArticle(Long id, Long userId) {
		articleCommandService.deleteArticle(id, userId);
	}

	@Caching(evict = {
		@CacheEvict(value = "articles::all", allEntries = true),
		@CacheEvict(value = "articles::popular", allEntries = true)
	})
	public void updateArticle(Long id, UpdateArticleRequest request, Long userId) {
		articleCommandService.updateArticle(id, request, userId);
	}
}
