package com.dnd.gooding.oauth.command.domain.repository;

import java.util.Optional;
import org.springframework.data.repository.Repository;

import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;

public interface OAuthRepository extends Repository<OAuth, OAuthId> {

    Optional<OAuth> findByoAuthId(OAuthId oAuthId);

    void save(OAuth oAuth);
}
