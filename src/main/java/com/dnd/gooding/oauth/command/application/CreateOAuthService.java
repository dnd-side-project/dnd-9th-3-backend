package com.dnd.gooding.oauth.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.common.event.Events;
import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.domain.OAuthRepository;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.domain.MemberCreatedEvent;

@Service
public class CreateOAuthService {

	private CreateMemberService createMemberService;
	private OAuthRepository oAuthRepository;

	private ExternalLogin externalLogin;

	public CreateOAuthService(CreateMemberService createMemberService,
		OAuthRepository oAuthRepository, ExternalLogin externalLogin) {
		this.createMemberService = createMemberService;
		this.oAuthRepository = oAuthRepository;
		this.externalLogin = externalLogin;
	}

	@Transactional
	public OAuth create(String code) {
		OAuthMember oAuthMember = externalLogin.getOauthToken(code);
		Events.handle((MemberCreatedEvent evt) ->
			createMemberService.create(evt.getEmail(), evt.getoAuthId().getId()));

		OAuth oAuth = new OAuth(
			new OAuthId(oAuthMember.getoAuthId()),
			oAuthMember.getImageUrl(),
			oAuthMember.getProvider(),
			oAuthMember.getEmail());
		
		createOAuth(oAuth);
		return oAuth;
	}

	@Transactional
	public void createOAuth(OAuth oAuth) {
		oAuthRepository
			.findByoAuthId(oAuth.getoAuthId())
			.ifPresentOrElse(x -> {
			}, () -> {
				oAuthRepository.save(oAuth);
			});
	}
}
