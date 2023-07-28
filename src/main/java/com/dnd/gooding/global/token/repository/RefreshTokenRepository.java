package com.dnd.gooding.global.token.repository;

import org.springframework.data.repository.CrudRepository;

import com.dnd.gooding.global.token.model.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
