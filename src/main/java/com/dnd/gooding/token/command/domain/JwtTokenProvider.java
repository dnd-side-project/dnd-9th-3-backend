package com.dnd.gooding.token.command.domain;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dnd.gooding.token.command.application.ExpiredTokenException;
import com.dnd.gooding.token.command.application.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final long MILLI_SECOND = 1000L;

	@Value("${jwt.issuer}")
	private String issuer;
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.expiry-seconds.access-token}")
	private long accessTokenExpirySeconds;

	public String createAccessToken(String memberId) {
		Map<String, Object> claims = Map.of("memberId", memberId);
		return this.createAccessToken(claims);
	}

	public String createAccessToken(Map<String, Object> claims) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + accessTokenExpirySeconds * MILLI_SECOND);

		return Jwts.builder()
			.setIssuer(issuer)
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException();
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException();
		}
	}
}
