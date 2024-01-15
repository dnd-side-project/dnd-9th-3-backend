package com.dnd.gooding.token.command.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

	public Token createTokens(OAuth oAuth) {
		String accessToken = createAccessToken(oAuth.getoAuthId().getId());
		String refreshToken = createRefreshToken(oAuth.getoAuthId().getId());
		return new Token(oAuth.getoAuthId().getId(), accessToken, refreshToken);
	}

	public String createAccessToken(String oauthId) {
		return jwtTokenProvider.createAccessToken(oauthId);
	}

	public String createRefreshToken(String oauthId) {
		String uuid = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(uuid, oauthId, refreshTokenExpirySeconds);
		return refreshTokenRepository.save(refreshToken).getRefreshToken();
	}
}
