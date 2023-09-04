package com.dnd.gooding.domain.user.service;

import static org.springframework.util.StringUtils.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.user.controller.port.UserService;
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

	@Transactional
	@Override
	public UserEntity create(OAuthUser oAuthUser) {
		return userRepository.findByProviderAndOauthId(oAuthUser.provider(), oAuthUser.oauthId())
			.orElseGet(() -> save(UserEntity.from(oAuthUser)));
	}

	@Transactional
	@Override
	public UserEntity update(Long userId, String nickName, MultipartFile profileImage) throws IOException {
		UserEntity userEntity = findByUserId(userId);
		if (!profileImage.isEmpty()) {
			String profileImgUrl = s3Service.upload(profileImage, userEntity);
			userEntity.changeImgUrl(profileImgUrl);
		}
		if (hasText(nickName)) {
			userEntity.changeNickName(nickName);
		}
		return save(userEntity);
	}

	@Override
	public UserEntity findByUserId(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Override
	public UserEntity findByOauthId(String oauthId) {
		return userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
	}

	@Transactional
	@Override
	public UserEntity save(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	@Transactional
	@Override
	public void delete(Long userId, String refreshToken) {
		userRepository.findByUserId(userId)
			.ifPresentOrElse(userEntity -> {
				userRepository.delete(userEntity);
				tokenService.deleteRefreshToken(refreshToken);
			}, () -> {
				throw new UserNotFoundException(userId);
			});
	}

	@Override
	public UserEntity findByUserIdAndOnboarding(Long userId) {
		return null;
	}
}
