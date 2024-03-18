package com.dnd.gooding.token.command.application.in;

public interface LogoutTokenUseCase {

    void deleteRefreshToken(String refreshToken);
}
