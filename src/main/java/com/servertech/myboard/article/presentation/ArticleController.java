package com.servertech.myboard.article.presentation;

import com.servertech.myboard.article.application.ArticleFacade;
import com.servertech.myboard.article.application.dto.request.CreateArticleRequest;
import com.servertech.myboard.article.application.dto.request.UpdateArticleRequest;
import com.servertech.myboard.article.application.dto.response.ArticleCommentResponse;
import com.servertech.myboard.article.application.dto.response.ArticleDetailResponse;
import com.servertech.myboard.article.application.dto.response.ArticleListResponse;
import com.servertech.myboard.article.application.dto.response.ArticleResponse;
import com.servertech.myboard.comment.application.CommentFacade;
import com.servertech.myboard.comment.application.dto.response.CommentListResponse;
import com.servertech.myboard.user.infra.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
	private final ArticleFacade articleFacade;
	private final CommentFacade commentFacade;

	@GetMapping
	public ResponseEntity<ArticleListResponse> getArticles(Pageable pageable) {
		ArticleListResponse response = articleFacade.findAll(pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/popular")
	public ResponseEntity<ArticleListResponse> getPopularArticles(Pageable pageable) {
		ArticleListResponse response = articleFacade.findPopular(pageable);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<ArticleResponse> createArticle(@RequestBody CreateArticleRequest request,
														 @AuthenticationPrincipal CustomUserDetails principal
	) {
		ArticleResponse response = articleFacade.save(request, principal.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ArticleCommentResponse> getArticleDetail(@PathVariable Long id) {
		ArticleDetailResponse response = articleFacade.find(id);
		CommentListResponse comments = commentFacade.findByArticleId(id);
		return ResponseEntity.status(HttpStatus.OK).body(ArticleCommentResponse.from(response, comments));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable Long id,
											  @AuthenticationPrincipal CustomUserDetails principal) {
		articleFacade.deleteArticle(id, principal.getId());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateArticle(@PathVariable Long id,
											  @RequestBody UpdateArticleRequest request,
											  @AuthenticationPrincipal CustomUserDetails principal) {
		articleFacade.updateArticle(id, request, principal.getId());
		return ResponseEntity.noContent().build();
	}
}
