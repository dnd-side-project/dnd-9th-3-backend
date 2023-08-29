package com.dnd.gooding.domain.user.service.port;

import java.util.Optional;

import com.dnd.gooding.domain.user.model.User;

public interface UserRepository {
	Optional<User> findByUserIdByProviderAndOauthId(String provider, String oauthId);
	Optional<User> findByOauthId(String oauthId);
	Optional<User> findByUserIdAndOnboarding(Long userId);
	void profileImageUpdate(User updateUser, String profileImageUrl);
	void nickNameUpdate(User updateUser, String nickName);
	Optional<User> findById(Long userId);
	void delete(User user);
	User save(User user);
}
