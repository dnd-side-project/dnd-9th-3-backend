package com.dnd.gooding.domain.user.repository;

import java.util.Optional;

import com.dnd.gooding.domain.user.model.User;

public interface UserRepositoryCustom {

	Optional<User> findByUserIdByProviderAndOauthId(String provider, String oauthId);
	Optional<User> findByOauthId(String oauthId);
	void profileImageUpdate(User updateUser, String profileImageUrl);
	void nickNameUpdate(User updateUser, String nickName);
}
