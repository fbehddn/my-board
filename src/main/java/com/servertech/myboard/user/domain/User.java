package com.servertech.myboard.user.domain;

import com.servertech.myboard.article.domain.Article;
import com.servertech.myboard.comment.domain.Comment;
import com.servertech.myboard.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseTimeEntity implements UserDetails {
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

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
