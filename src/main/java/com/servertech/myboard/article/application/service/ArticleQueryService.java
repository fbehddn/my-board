package com.servertech.myboard.article.application.service;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.article.domain.ArticleRepository;
import com.servertech.myboard.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleQueryService {
	private final ArticleRepository articleRepository;

	public Page<Article> findAll(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}

	public Page<Article> findAllByPopular(Pageable pageable) {
		return articleRepository.findAllByOrderByLikeCountDesc(pageable);
	}

	public Article find(Long id) {
		return articleRepository.find(id).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다: id=" + id));
	}
}
