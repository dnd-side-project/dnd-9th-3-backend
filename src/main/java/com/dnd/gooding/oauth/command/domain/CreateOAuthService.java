package com.dnd.gooding.oauth.command.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOAuthService {

    private OAuthRepository oAuthRepository;

    public CreateOAuthService(OAuthRepository oAuthRepository) {
        this.oAuthRepository = oAuthRepository;
    }

    @Transactional
    public void createOAuth(OAuth oAuth) {
        oAuthRepository.findById(oAuth.getoAuthId()).ifPresentOrElse(
            x -> {}, () -> {
                oAuthRepository.save(oAuth);
            }
        );
    }
}
