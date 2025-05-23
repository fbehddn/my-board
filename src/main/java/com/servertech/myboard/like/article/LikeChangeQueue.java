package com.servertech.myboard.like.article;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class LikeChangeQueue {
	private final BlockingQueue<LikeChange> queue = new LinkedBlockingQueue<>();

	public void enqueue(LikeChange likeChange) {
		queue.add(likeChange);
	}

	public List<LikeChange> drain() {
		List<LikeChange> list = new ArrayList<>();
		queue.drainTo(list);

		return list;
	}
}