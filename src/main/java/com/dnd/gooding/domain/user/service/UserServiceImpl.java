package com.dnd.gooding.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.user.controller.request.UpdateUserRequest;
import com.dnd.gooding.domain.user.controller.response.UserProfileResponse;
import com.dnd.gooding.domain.user.controller.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserRepository;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	public static final String DEFAULT_ROLE = "ROLE_USER";

	private final UserRepository userRepository;

	@Transactional
	@Override
	public AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo) {
		User user = userRepository.findByUserIdByProviderAndOauthId(oauthUserInfo.provider(), oauthUserInfo.oauthId())
			.orElseGet(() -> save(User.from(oauthUserInfo)));
		return new AuthUserInfo(user.getId(), DEFAULT_ROLE, user.getNickname());
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public UserProfileResponse getById(Long userId) {
		return UserProfileResponse.from(
			userRepository.findById(userId)
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
