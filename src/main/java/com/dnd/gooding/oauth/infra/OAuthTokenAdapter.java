package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.out.OAuthTokenPort;
import com.dnd.gooding.token.command.application.in.CreateTokenUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthTokenAdapter implements OAuthTokenPort {

    private final CreateTokenUseCase createTokenUseCase;

    public OAuthTokenAdapter(CreateTokenUseCase createTokenUseCase) {
        this.createTokenUseCase = createTokenUseCase;
    }

    @Transactional
    @Override
    public Token createTokens(String id) {
        return createTokenUseCase.createTokens(id);
    }
}
