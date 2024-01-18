package com.dnd.gooding.oauth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.CreateOAuthService;
import com.dnd.gooding.oauth.command.application.SocialLoginRequest;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.token.command.application.TokenService;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.util.CookieUtil;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthLoginController {

	@Autowired
	private CreateOAuthService createOAuthService;
	@Autowired
	private CreateMemberService createMemberService;
	@Autowired
	private TokenService tokenService;

	@PostMapping(value = "/login")
	public ResponseEntity<Token> oauthLogin(
		HttpServletRequest request,
		HttpServletResponse response,
		@RequestBody SocialLoginRequest socialLoginRequest) {
		OAuth oAuth = createOAuthService.createOAuth(socialLoginRequest.getCode());
		Token token = tokenService.createTokens(oAuth.getoAuthId().getId());
		return ResponseEntity
			.ok()
			.body(token);
	}
}
