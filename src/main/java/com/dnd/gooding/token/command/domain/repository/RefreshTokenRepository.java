package com.dnd.gooding.token.command.domain.repository;

import com.dnd.gooding.token.command.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {}
