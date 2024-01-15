package com.dnd.gooding.oauth.command.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface OAuthRepository extends Repository<OAuth, String> {

    Optional<OAuth> findById(String oauthId);

    void save(OAuth oAuth);
}
