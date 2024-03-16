package com.dnd.gooding.oauth.command.application.out;

import com.dnd.gooding.common.model.Token;

public interface OAuthTokenPort {

    Token createTokens(String id);
}
