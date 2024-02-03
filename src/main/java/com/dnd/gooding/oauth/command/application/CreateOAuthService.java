package com.dnd.gooding.oauth.command.application;

import com.dnd.gooding.common.event.Events;
import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.oauth.command.domain.OAuthRepository;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import com.dnd.gooding.oauth.infra.MemberCreatedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOAuthService {

  private OAuthRepository oAuthRepository;

  private ExternalLogin externalLogin;

  @Autowired private MemberCreatedHandler memberCreatedHandler;

  public CreateOAuthService(OAuthRepository oAuthRepository, ExternalLogin externalLogin) {
    this.oAuthRepository = oAuthRepository;
    this.externalLogin = externalLogin;
  }

  @Transactional
  public OAuth create(String code) {
    OAuthMember oAuthMember = externalLogin.getOauthToken(code);
    Events.handle(memberCreatedHandler);

    OAuth oAuth =
        new OAuth(
            new OAuthId(oAuthMember.getoAuthId()),
            oAuthMember.getImageUrl(),
            oAuthMember.getProvider(),
            oAuthMember.getEmail());

    createOAuth(oAuth);
    return oAuth;
  }

  @Transactional
  public void createOAuth(OAuth oAuth) {
    oAuthRepository
        .findByoAuthId(oAuth.getoAuthId())
        .ifPresentOrElse(
            x -> {},
            () -> {
              oAuthRepository.save(oAuth);
            });
  }
}
