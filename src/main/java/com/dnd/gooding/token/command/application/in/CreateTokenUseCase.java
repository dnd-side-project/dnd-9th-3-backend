package com.dnd.gooding.token.command.application.in;

import com.dnd.gooding.common.model.Token;

public interface CreateTokenUseCase {

    String getAccessTokensByRefreshToken(String refreshToken);

    Token createTokens(String id);
}
