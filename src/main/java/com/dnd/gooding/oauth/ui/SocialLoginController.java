package com.dnd.gooding.oauth.ui;

import com.dnd.gooding.oauth.infra.ExternalGoogleLogin;
import com.dnd.gooding.oauth.infra.ExternalKakaoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.OAuthLoginService;

@RestController
@RequestMapping("/api/v1/auth")
public class SocialLoginController {

	@Autowired
	private OAuthLoginService OAuthLoginService;

	@GetMapping(value = "/kakao")
	public ResponseEntity<Token> kakao(
		@RequestParam String code) {
		OAuthLoginService OAuthLoginService =
				new OAuthLoginService(new ExternalKakaoLogin());

		return ResponseEntity
			.ok()
			.body(OAuthLoginService.getAccessToken(code));
	}

	@GetMapping(value = "/google")
	public ResponseEntity<Token> google(
			@RequestParam String code) {
		OAuthLoginService OAuthLoginService =
				new OAuthLoginService(new ExternalGoogleLogin());

		return ResponseEntity
				.ok()
				.body(OAuthLoginService.getAccessToken(code));
	}
}
