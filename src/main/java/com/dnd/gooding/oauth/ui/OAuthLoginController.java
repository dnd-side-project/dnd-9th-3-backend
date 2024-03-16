package com.dnd.gooding.oauth.ui;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.in.CreateOAuthUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthLoginController {

    private final CreateOAuthUseCase createOAuthUseCase;

    public OAuthLoginController(CreateOAuthUseCase createOAuthUseCase) {
        this.createOAuthUseCase = createOAuthUseCase;
    }

    @GetMapping(value = "/login")
    public ResponseEntity<Token> login(@RequestParam String code) {
        Token token = createOAuthUseCase.create(code);
        return ResponseEntity.ok().body(token);
    }
}
