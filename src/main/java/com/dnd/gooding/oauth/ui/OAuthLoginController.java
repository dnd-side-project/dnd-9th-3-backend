package com.dnd.gooding.oauth.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.CreateOAuthService;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.token.command.application.TokenService;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthLoginController {

	@Autowired
	private CreateOAuthService createOAuthService;
	@Autowired
	private TokenService tokenService;

	@GetMapping(value = "/login/{code}")
	public ResponseEntity<Token> login(@PathVariable String code) {
		OAuth oAuth = createOAuthService.create(code);
		Token token = tokenService.createTokens(oAuth.getEmail());
		return ResponseEntity.ok().body(token);
	}
}
