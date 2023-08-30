package com.dnd.gooding.domain.user.service;

import static org.springframework.util.StringUtils.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.user.controller.port.UserService;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;
import com.dnd.gooding.domain.user.service.port.UserRepository;
import com.dnd.gooding.global.oauth.domain.OAuthUser;
import com.dnd.gooding.global.s3.controller.port.S3Service;
import com.dnd.gooding.global.token.service.TokenService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TokenService tokenService;
	private final S3Service s3Service;

	@Override
	public User create(OAuthUser oAuthUser) {
		return userRepository.findByProviderAndOauthId(oAuthUser.provider(), oAuthUser.oauthId())
			.orElseGet(() -> save(User.from(oAuthUser)));
	}

	@Override
	public User update(Long userId, String nickName, MultipartFile profileImage) throws IOException {
		User user = findByUserId(userId);
		if (!profileImage.isEmpty()) {
			String profileImgUrl = s3Service.upload(profileImage, user);
			user = user.profileImageUrlUpdate(profileImgUrl);
		}
		if (hasText(nickName)) {
			user = user.nickNameUpdate(nickName);
		}
		return save(user);
	}

	@Override
	public User findByUserId(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Override
	public User findByOauthId(String oauthId) {
		return userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
	}

	@Override
	public User save(User user) {
		return userRepository.save(UserEntity.from(user));
	}

	@Override
	public void delete(Long userId, String refreshToken) {
		userRepository.findByUserId(userId)
			.ifPresentOrElse(user -> {
				userRepository.delete(UserEntity.from(user));
				tokenService.deleteRefreshToken(refreshToken);
			}, () -> {
				throw new UserNotFoundException(userId);
			});
	}

	@Override
	public User findByUserIdAndOnboarding(Long userId) {
		return null;
	}
}
