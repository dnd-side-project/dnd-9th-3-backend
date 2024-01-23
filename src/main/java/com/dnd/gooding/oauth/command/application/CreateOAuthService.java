package com.dnd.gooding.oauth.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.domain.OAuthRepository;
import com.dnd.gooding.oauth.command.model.OAuthMember;

@Service
public class CreateOAuthService {

	private OAuthRepository oAuthRepository;

	private ExternalLogin externalLogin;

	public CreateOAuthService(OAuthRepository oAuthRepository, ExternalLogin externalLogin) {
		this.oAuthRepository = oAuthRepository;
		this.externalLogin = externalLogin;
	}

	public OAuth createOAuth(String code) {
		OAuthMember oAuthMember = externalLogin.getOauthToken(code);
		OAuth oAuth = new OAuth(new OAuthId(
			oAuthMember.getoAuthId()),
			oAuthMember.getImageUrl(),
			oAuthMember.getProvider(),
			oAuthMember.getEmail());
		createOAuth(oAuth);
		return oAuth;
	}

	@Transactional
	public void createOAuth(OAuth oAuth) {
		oAuthRepository.findByoAuthId(oAuth.getoAuthId()).ifPresentOrElse(
			x -> {
			}, () -> {
				oAuthRepository.save(oAuth);
			}
		);
	}
}
