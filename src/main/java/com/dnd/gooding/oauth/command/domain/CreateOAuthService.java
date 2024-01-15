package com.dnd.gooding.oauth.command.domain;

import org.springframework.stereotype.Service;

@Service
public class CreateOAuthService {

    private OAuthRepository oAuthRepository;

    public CreateOAuthService(OAuthRepository oAuthRepository) {
        this.oAuthRepository = oAuthRepository;
    }

    public void createOAuth(OAuth oAuth) {
    }
}
