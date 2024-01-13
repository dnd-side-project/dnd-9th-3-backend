package com.dnd.gooding.token.command.application;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.common.model.UserRole;
import com.dnd.gooding.token.command.domain.RefreshToken;
import com.dnd.gooding.token.command.domain.RefreshTokenRepository;
import com.dnd.gooding.user.command.domain.oauthmember.OAuthMember;
import com.dnd.gooding.user.command.domain.oauthmember.OAuthMemberId;

@Service
public class TokenService {

	private RefreshTokenRepository refreshTokenRepository;
	private JwtTokenProvider jwtTokenProvider;
	@Value("${jwt.expiry-seconds.refresh-token:36000}")
	private int refreshTokenExpirySeconds;

	public TokenService(
		JwtTokenProvider jwtTokenProvider,
		RefreshTokenRepository refreshTokenRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public Token createTokens(OAuthMember oAuthMember) {
		OAuthMemberId oAuthMemberId = oAuthMember.getoAuthMemberId();
		String userRole = UserRole.ROLE_USER.name();

		String accessToken = createAccessToken(oAuthMemberId.getId(), userRole);
		String refreshToken = createRefreshToken(oAuthMemberId.getId(), userRole);

		return new Token(oAuthMemberId.getId(), accessToken, refreshToken);
	}

	public String createAccessToken(String oauthId, String userRole) {
		return jwtTokenProvider.createAccessToken(oauthId, userRole);
	}

	public String createRefreshToken(String oauthId, String userRole) {
		String uuid = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(uuid, oauthId, userRole, refreshTokenExpirySeconds);

		return refreshTokenRepository.save(refreshToken).getRefreshToken();
	}
}
