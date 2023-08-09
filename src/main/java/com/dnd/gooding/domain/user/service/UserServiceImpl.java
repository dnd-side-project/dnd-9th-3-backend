package com.dnd.gooding.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.user.dto.request.UpdateUserRequest;
import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserJpaRepository;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	public static final String DEFAULT_ROLE = "ROLE_USER";

	private final UserJpaRepository userJpaRepository;

	@Transactional
	@Override
	public AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo) {
		User user = userJpaRepository.findByUserIdByProviderAndOauthId(oauthUserInfo.provider(), oauthUserInfo.oauthId())
			.orElseGet(() -> save(User.from(oauthUserInfo)));
		return new AuthUserInfo(user.getId(), DEFAULT_ROLE, user.getNickname(), user.getOauthId());
	}

	public User save(User user) {
		return userJpaRepository.save(user);
	}

	@Transactional
	@Override
	public UserProfileResponse getById(Long userId) {
		return UserProfileResponse.from(
			userJpaRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId))
		);
	}

	@Override
	public UserProfileResponse updateUserProfile(UpdateUserRequest updateUserRequest, Long userId) {
		return null;
	}

	@Override
	public void deleteUser(Long userId, String refreshToken) {

	}

}
