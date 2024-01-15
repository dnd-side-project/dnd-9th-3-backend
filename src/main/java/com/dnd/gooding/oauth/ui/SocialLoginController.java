package com.dnd.gooding.oauth.ui;

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

	private OAuthLoginService OAuthLoginService;

	public SocialLoginController(OAuthLoginService OAuthLoginService) {
		this.OAuthLoginService = OAuthLoginService;
	}

	@GetMapping
	public ResponseEntity<Token> kakao(
		@RequestParam String code) {
		return ResponseEntity
			.ok()
			.body(OAuthLoginService.getAccessToken(code));
	}
}
