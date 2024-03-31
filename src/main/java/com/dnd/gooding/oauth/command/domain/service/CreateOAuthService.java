package com.dnd.gooding.oauth.command.domain.service;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.oauth.command.application.in.CreateOAuthUseCase;
import com.dnd.gooding.oauth.command.application.out.ExternalLoginPort;
import com.dnd.gooding.oauth.command.application.out.OAuthMemberPort;
import com.dnd.gooding.oauth.command.application.out.OAuthTokenPort;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.domain.dto.OAuthMember;
import com.dnd.gooding.oauth.command.domain.event.LoginSuccessEvent;
import com.dnd.gooding.oauth.command.domain.repository.OAuthRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CreateOAuthService implements CreateOAuthUseCase {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final OAuthRepository oAuthRepository;

    private final ExternalLoginPort externalLoginPort;

    private final OAuthMemberPort oAuthMemberPort;

    private final OAuthTokenPort oAuthTokenPort;

    public CreateOAuthService(
            ApplicationEventPublisher applicationEventPublisher,
            OAuthRepository oAuthRepository,
            ExternalLoginPort externalLoginPort,
            OAuthMemberPort oAuthMemberPort,
            OAuthTokenPort oAuthTokenPort) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.oAuthRepository = oAuthRepository;
        this.externalLoginPort = externalLoginPort;
        this.oAuthMemberPort = oAuthMemberPort;
        this.oAuthTokenPort = oAuthTokenPort;
    }

    @Transactional
    public Token create(String code) {
        OAuthMember oAuthMember = externalLoginPort.getOauthToken(code);
        OAuthId oAuthId = new OAuthId(oAuthMember.getoAuthId());
        OAuth oAuth =
                OAuth.builder()
                        .oAuthId(oAuthId)
                        .imageUrl(oAuthMember.getImageUrl())
                        .provider(oAuthMember.getProvider())
                        .email(oAuthMember.getEmail())
                        .build();
        save(oAuth);
        oAuthMemberPort.create(oAuthMember.getEmail(), oAuthId.getId());
        Token token = oAuthTokenPort.createTokens(oAuth.getEmail());
        if (StringUtils.hasText(token.getAccessToken())) {
            applicationEventPublisher.publishEvent(new LoginSuccessEvent(oAuth.getEmail()));
        }
        return token;
    }

    private void save(OAuth oAuth) {
        oAuthRepository
                .findByoAuthId(oAuth.getoAuthId())
                .ifPresentOrElse(x -> {}, () -> oAuthRepository.save(oAuth));
    }
}
