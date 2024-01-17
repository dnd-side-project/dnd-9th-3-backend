package com.dnd.gooding.oauth.ui;

import com.dnd.gooding.oauth.command.application.SocialLoginRequest;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.token.command.application.CreateTokenService;
import com.dnd.gooding.user.command.application.MemberService;
import com.dnd.gooding.user.command.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.OAuthLoginService;

@RestController
@RequestMapping("/api/v1/auth")
public class SocialLoginController {

	@Autowired
	private OAuthLoginService oAuthLoginService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CreateTokenService createTokenService;

	@PostMapping
	public ResponseEntity<Token> auth(
			@RequestBody SocialLoginRequest socialLoginRequest){
		OAuth oAuth = oAuthLoginService.getOauthMember(socialLoginRequest.getCode());
		Member member = memberService.createMember(socialLoginRequest.getMemberId(), oAuth.getoAuthId());
		return ResponseEntity
			.ok()
			.body(createTokenService.createTokens(member));
	}
}
