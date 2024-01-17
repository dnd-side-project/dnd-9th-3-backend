package com.dnd.gooding.oauth.ui;

import com.dnd.gooding.oauth.command.application.SocialLoginRequest;
import com.dnd.gooding.oauth.command.application.CreateOAuthService;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.token.command.application.CreateTokenService;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dnd.gooding.common.model.Token;

@RestController
@RequestMapping("/api/v1/auth")
public class SocialLoginController {

	@Autowired
	private CreateOAuthService createOAuthService;
	@Autowired
	private CreateMemberService createMemberService;
	@Autowired
	private CreateTokenService createTokenService;

	@PostMapping
	public ResponseEntity<Token> auth(
			@RequestBody SocialLoginRequest socialLoginRequest){
		OAuth oAuth = createOAuthService.getOauthMember(socialLoginRequest.getCode());
		Member member = createMemberService.createMember(socialLoginRequest.getMemberId(), oAuth.getoAuthId());
		return ResponseEntity
			.ok()
			.body(createTokenService.createTokens(member));
	}
}
