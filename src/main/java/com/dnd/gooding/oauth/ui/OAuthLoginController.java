package com.dnd.gooding.oauth.ui;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.CreateOAuthService;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthLoginController {

    private final CreateOAuthService createOAuthService;
    private final OAuthTokenService oAuthTokenService;

    public OAuthLoginController(
            CreateOAuthService createOAuthService, OAuthTokenService oAuthTokenService) {
        this.createOAuthService = createOAuthService;
        this.oAuthTokenService = oAuthTokenService;
    }

    @GetMapping(value = "/login/{code}")
    public ResponseEntity<Token> login(@PathVariable String code) {
        OAuth oAuth = createOAuthService.create(code);
        Token token = oAuthTokenService.createTokens(oAuth.getEmail());
        return ResponseEntity.ok().body(token);
    }
}
