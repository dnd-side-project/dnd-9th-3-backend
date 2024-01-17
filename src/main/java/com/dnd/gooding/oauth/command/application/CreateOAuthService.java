package com.dnd.gooding.oauth.command.application;

import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.domain.OAuthRepository;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOAuthService {

	private OAuthRepository oAuthRepository;

	private ExternalLogin externalLogin;

	public CreateOAuthService(OAuthRepository oAuthRepository, ExternalLogin externalLogin) {
		this.oAuthRepository = oAuthRepository;
		this.externalLogin = externalLogin;
	}

	public OAuth getOauthMember(String code) {
		OAuthMember oAuthMember = externalLogin.getOauthToken(code);
		OAuth oAuth = new OAuth(new OAuthId(
				oAuthMember.getoAuthId()),
				oAuthMember.getImageUrl(),
				oAuthMember.getProvider());
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
