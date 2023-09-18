package com.dnd.gooding.domain.user.service.port;

import java.util.Optional;

import com.dnd.gooding.domain.user.domain.User;

public interface UserRepository {
	Optional<User> findByProviderAndOauthId(String provider, String oauthId);
	Optional<User> findByUserId(Long userId);
	Optional<User> findByOauthId(String oauthId);
	User save(User user);
	void delete(User user);
	Optional<User> findByUserIdAndOnboarding(Long userId);
}
