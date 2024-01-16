package com.dnd.gooding.token.command.application;

import org.springframework.stereotype.Service;

import com.dnd.gooding.token.command.domain.JwtAuthentication;
import com.dnd.gooding.token.command.domain.JwtAuthenticationToken;
import com.dnd.gooding.token.command.domain.JwtTokenProvider;

import io.jsonwebtoken.Claims;

@Service
public class TokenService {

	private JwtTokenProvider jwtTokenProvider;

	public TokenService(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public JwtAuthenticationToken getAuthenticationByAccessToken(String accessToken) {
		jwtTokenProvider.validateToken(accessToken);
		Claims claims = jwtTokenProvider.getClaims(accessToken);
		String oauthId = claims.get("oauthId", String.class);
		JwtAuthentication principal = new JwtAuthentication(oauthId, accessToken);
		return new JwtAuthenticationToken(principal, null);
	}
}
