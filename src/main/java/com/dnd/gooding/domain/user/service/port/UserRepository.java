package com.dnd.gooding.domain.user.service.port;

import java.util.Optional;

import com.dnd.gooding.domain.user.infrastructure.UserEntity;

public interface UserRepository {
	Optional<UserEntity> findByProviderAndOauthId(String provider, String oauthId);
	Optional<UserEntity> findByUserId(Long userId);
	Optional<UserEntity> findByOauthId(String oauthId);
	UserEntity save(UserEntity userEntity);
	void delete(UserEntity userEntity);
	Optional<UserEntity> findByUserIdAndOnboarding(Long userId);
}
