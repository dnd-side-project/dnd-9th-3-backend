package com.dnd.gooding.domain.user.service;

import com.dnd.gooding.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserRepository;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	public static final String DEFAULT_ROLE = "ROLE_USER";
	private final S3Service s3Service;
	private final UserRepository userRepository;

	@Transactional
	@Override
	public AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo) {
		User user = userRepository.findByUserIdByProviderAndOauthId(oauthUserInfo.provider(), oauthUserInfo.oauthId())
			.orElseGet(() -> save(User.from(oauthUserInfo)));
		return new AuthUserInfo(user.getId(), DEFAULT_ROLE, user.getNickname(), user.getOauthId());
	}

	@Override
	public UserProfileResponse getByOauthId(String oauthId) {
		return UserProfileResponse.from(
			userRepository.findByOauthId(oauthId)
				.orElseThrow(() -> new UserNotFoundException(oauthId))
		);
	}

	@Transactional
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public void delete(Long userId, String refreshToken) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		userRepository.delete(user);
	}

	@Transactional
	@Override
	public void update(Long userId, String nickName, MultipartFile profileImage) throws IOException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		if (!profileImage.isEmpty()) {
			String profileImageUrl =
					s3Service.userImageUpload(profileImage, user);
			userRepository.profileImageUpdate(user, profileImageUrl);
		}
		if (hasText(nickName)) {
			userRepository.nickNameUpdate(user, nickName);
		}
	}

}
