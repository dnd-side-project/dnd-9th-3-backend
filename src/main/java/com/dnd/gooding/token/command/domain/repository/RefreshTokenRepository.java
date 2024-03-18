package com.dnd.gooding.token.command.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.dnd.gooding.token.command.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {}
