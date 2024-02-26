package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.domain.OAuthTokenService;
import com.dnd.gooding.token.command.application.TokenService;
import org.springframework.transaction.annotation.Transactional;

public class OAuthTokenServiceImpl implements OAuthTokenService {

    private TokenService tokenService;

    @Transactional
    @Override
    public Token createTokens(String id) {
        return tokenService.createTokens(id);
    }
}
