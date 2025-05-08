package com.servertech.myboard.user.domain;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String username;

	@OneToMany(mappedBy = "user")
	private List<Article> articles;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	public static User create(String email, String password, String username) {
		return User.builder()
			.email(email)
			.password(password)
			.username(username)
			.build();
	}

	public boolean isPasswordMatch(String rawPassword, PasswordEncoder encoder) {
		return encoder.matches(rawPassword, this.password);
	}
}
