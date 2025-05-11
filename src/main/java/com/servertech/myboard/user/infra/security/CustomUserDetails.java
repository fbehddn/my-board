package com.servertech.myboard.user.infra.security;

import com.servertech.myboard.user.domain.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails, Serializable {
	private final Long id;
	private final String email;
	private final String password;
	private final String username;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;

	public static CustomUserDetails from(User user) {
		return CustomUserDetails.builder()
			.id(user.getId())
			.email(user.getEmail())
			.password(user.getPassword())
			.username(user.getUsername())
			.authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
			.enabled(true)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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
		return enabled;
	}
}
