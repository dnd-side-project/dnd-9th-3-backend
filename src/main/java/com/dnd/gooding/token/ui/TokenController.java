package com.dnd.gooding.token.ui;

import com.dnd.gooding.token.command.application.in.CreateTokenUseCase;
import com.dnd.gooding.token.command.domain.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final CreateTokenUseCase createTokenUseCase;

    public TokenController(CreateTokenUseCase createTokenUseCase) {
        this.createTokenUseCase = createTokenUseCase;
    }

    @PostMapping(value = "/reissue")
    public ResponseEntity<String> reissue(@CookieValue("refreshToken") String refreshToken) {
        return ResponseEntity.ok().body(createTokenUseCase.getAccessTokensByRefreshToken(refreshToken));
    }
}
