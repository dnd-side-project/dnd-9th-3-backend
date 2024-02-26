package com.dnd.gooding.oauth.command.domain;

import com.dnd.gooding.common.model.Token;

public interface OAuthTokenService {

    Token createTokens(String id);
}
