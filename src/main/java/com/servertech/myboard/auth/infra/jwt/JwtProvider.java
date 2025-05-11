package com.servertech.myboard.auth.infra.jwt;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

import com.servertech.myboard.user.infra.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
	private final JwtProperties jwtProperties;
	private final CustomUserDetailService userDetailsService;
	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";

	public String generateAccessToken(String email) {
		return makeToken(Duration.ofMinutes(15), email);
	}

	public String generateRefreshToken(String email) {
		return makeToken(Duration.ofDays(14), email);
	}

	private String makeToken(Duration ttl, String email) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + ttl.toMillis());

		return Jwts.builder()
			.setHeaderParam(TYPE, JWT_TYPE)
			.setIssuer(jwtProperties.getIssuer())
			.setIssuedAt(now)
			.setExpiration(expiry)
			.setSubject(email)
			.signWith(HS256, jwtProperties.getSecretKey())
			.compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public boolean validateToken(String token) {
		if (token == null) {
			return false;
		}

		Jwts.parser()
			.setSigningKey(jwtProperties.getSecretKey())
			.parseClaimsJws(token);
		return true;

	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getClaims(token).getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(jwtProperties.getSecretKey())
			.parseClaimsJws(token)
			.getBody();
	}
}
