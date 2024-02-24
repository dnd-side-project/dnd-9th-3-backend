package com.dnd.gooding.oauth.command.application;

import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.domain.OAuthRepository;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import com.dnd.gooding.user.command.domain.MemberCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOAuthService {

    private final OAuthRepository oAuthRepository;

    private final ExternalLogin externalLogin;

    @Autowired private ApplicationEventPublisher publisher;

    public CreateOAuthService(OAuthRepository oAuthRepository, ExternalLogin externalLogin) {
        this.oAuthRepository = oAuthRepository;
        this.externalLogin = externalLogin;
    }

    @Transactional
    public OAuth create(String code) {
        OAuthMember oAuthMember = externalLogin.getOauthToken(code);
        OAuthId oAuthId = new OAuthId(oAuthMember.getoAuthId());
        OAuth oAuth =
                new OAuth(
                        oAuthId, oAuthMember.getImageUrl(), oAuthMember.getProvider(), oAuthMember.getEmail());
        save(oAuth);
        publisher.publishEvent(new MemberCreatedEvent(oAuthMember.getEmail(), oAuthId));
        return oAuth;
    }

    private void save(OAuth oAuth) {
        oAuthRepository
                .findByoAuthId(oAuth.getoAuthId())
                .ifPresentOrElse(x -> {}, () -> oAuthRepository.save(oAuth));
    }
}
