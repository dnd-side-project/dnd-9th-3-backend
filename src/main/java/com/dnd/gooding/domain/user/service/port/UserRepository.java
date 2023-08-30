package com.dnd.gooding.domain.user.service.port;

import java.util.Optional;

import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;

public interface UserRepository {
	Optional<User> findByProviderAndOauthId(String provider, String oauthId);
	Optional<User> findByUserId(Long userId);
	Optional<User> findByOauthId(String oauthId);
	User save(UserEntity userEntity);
	void delete(UserEntity userEntity);
	Optional<User> findByUserIdAndOnboarding(Long userId);
}
