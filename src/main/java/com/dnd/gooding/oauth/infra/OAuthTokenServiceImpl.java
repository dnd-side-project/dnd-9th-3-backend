package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.domain.OAuthTokenService;
import com.dnd.gooding.token.command.application.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthTokenServiceImpl implements OAuthTokenService {

    private final TokenService tokenService;

    public OAuthTokenServiceImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Transactional
    @Override
    public Token createTokens(String id) {
        return tokenService.createTokens(id);
    }
}
