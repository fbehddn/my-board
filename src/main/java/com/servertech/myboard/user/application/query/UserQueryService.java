package com.servertech.myboard.user.application.query;

import com.servertech.myboard.global.exception.EntityNotFoundException;
import com.servertech.myboard.user.application.dto.response.UserDetailResponse;
import com.servertech.myboard.user.domain.User;
import com.servertech.myboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserDetailResponse getUserDetail(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return UserDetailResponse.from(user);
	}
}

