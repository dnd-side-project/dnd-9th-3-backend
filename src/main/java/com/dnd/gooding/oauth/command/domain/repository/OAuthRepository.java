package com.dnd.gooding.oauth.command.domain.repository;

import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface OAuthRepository extends Repository<OAuth, OAuthId> {

    Optional<OAuth> findByoAuthId(OAuthId oAuthId);

    void save(OAuth oAuth);
}
