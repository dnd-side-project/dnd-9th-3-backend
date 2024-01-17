package com.dnd.gooding.token.command.application;

import java.util.UUID;

import com.dnd.gooding.user.command.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.token.command.domain.JwtTokenProvider;
import com.dnd.gooding.token.command.domain.RefreshToken;
import com.dnd.gooding.token.command.domain.RefreshTokenRepository;

@Service
public class CreateTokenService {

	private JwtTokenProvider jwtTokenProvider;
	private RefreshTokenRepository refreshTokenRepository;
	@Value("${jwt.expiry-seconds.refresh-token:36000}")
	private int refreshTokenExpirySeconds;

	public CreateTokenService(
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

	public String createAccessToken(String memberId) {
		return jwtTokenProvider.createAccessToken(memberId);
	}

	@Transactional
	public String createRefreshToken(String memberId) {
		String uuid = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(uuid, memberId, refreshTokenExpirySeconds);
		return refreshTokenRepository.save(refreshToken).getRefreshToken();
	}
}
