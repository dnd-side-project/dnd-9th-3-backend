package com.dnd.gooding.oauth.command.application.in;

import com.dnd.gooding.common.model.Token;

public interface CreateOAuthUseCase {

    Token create(String code);
}
