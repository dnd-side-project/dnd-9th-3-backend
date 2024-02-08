package com.dnd.gooding.token.ui;

import com.dnd.gooding.token.command.application.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final int COOKIE_EXPIRE_SECONDS = 180;
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/reissue")
    public ResponseEntity<String> reissue(@CookieValue("refreshToken") String refreshToken) {
        return ResponseEntity.ok().body(tokenService.getAccessTokensByRefreshToken(refreshToken));
    }
}
