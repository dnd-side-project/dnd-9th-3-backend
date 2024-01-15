package com.dnd.gooding.oauth.command.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.domain.CreateOAuthService;
import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import com.dnd.gooding.token.command.application.CreateTokenService;

@Service
public class OAuthLoginService {

	@Autowired
	private CreateTokenService createTokenService;
	@Autowired
	private CreateOAuthService createOAuthService;
	private ExternalLogin externalLogin;

	public OAuthLoginService(ExternalLogin externalLogin) {
		this.externalLogin = externalLogin;
	}

	public Token getAccessToken(String code) {
		OAuthMember oAuthMember = externalLogin.getOauthToken(code);
		OAuth oAuth = new OAuth(new OAuthId(oAuthMember.getoAuthId()), oAuthMember.getImageUrl(),
			oAuthMember.getProvider());
		createOAuthService.createOAuth(oAuth);
		return createTokenService.createTokens(oAuth);
	}
}
