package com.dnd.gooding.token.command.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.token.command.domain.JwtTokenProvider;
import com.dnd.gooding.token.command.domain.RefreshToken;
import com.dnd.gooding.token.command.domain.RefreshTokenRepository;
import com.dnd.gooding.token.command.model.JwtAuthentication;
import com.dnd.gooding.token.command.model.JwtAuthenticationToken;
import com.dnd.gooding.user.command.domain.Member;

import io.jsonwebtoken.Claims;

@Service
public class TokenService {

	private JwtTokenProvider jwtTokenProvider;
	private RefreshTokenRepository refreshTokenRepository;
	@Value("${jwt.expiry-seconds.refresh-token:36000}")
	private int refreshTokenExpirySeconds;

	public TokenService(
		JwtTokenProvider jwtTokenProvider,
		RefreshTokenRepository refreshTokenRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public Token createTokens(Member member) {
		String accessToken = createAccessToken(member.getId().getId());
		String refreshToken = createRefreshToken(member.getId().getId());
		return new Token(member.getId().getId(), accessToken, refreshToken);
	}

	public JwtAuthenticationToken getAuthenticationByAccessToken(String accessToken) {
		jwtTokenProvider.validateToken(accessToken);
		Claims claims = jwtTokenProvider.getClaims(accessToken);
		String memberId = claims.get("memberId", String.class);
		JwtAuthentication principal = new JwtAuthentication(memberId, accessToken);
		return new JwtAuthenticationToken(principal, null);
	}

	private String createAccessToken(String memberId) {
		return jwtTokenProvider.createAccessToken(memberId);
	}

	@Transactional
	public String createRefreshToken(String memberId) {
		String uuid = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(uuid, memberId, refreshTokenExpirySeconds);
		return refreshTokenRepository.save(refreshToken).getRefreshToken();
	}

	@Transactional
	public String getAccessTokensByRefreshToken(String refreshToken) {
		return refreshTokenRepository.findById(refreshToken)
			.map(token -> createAccessToken(token.getMemberId()))
			.orElseThrow(RefreshTokenNotFoundException::new);
	}

	@Transactional
	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.findById(refreshToken)
			.ifPresent(refreshTokenRepository::delete);
	}
}
