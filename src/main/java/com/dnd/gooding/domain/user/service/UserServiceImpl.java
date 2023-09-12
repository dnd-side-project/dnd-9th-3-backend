package com.dnd.gooding.domain.user.service;

import static org.springframework.util.StringUtils.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.file.controller.port.FileService;
import com.dnd.gooding.domain.file.domain.FileCreate;
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

@Builder
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TokenService tokenService;
	private final S3Service s3Service;
	private final FileService fileService;

	@Transactional
	@Override
	public User create(OAuthUser oAuthUser) {
		return userRepository.findByProviderAndOauthId(oAuthUser.provider(), oAuthUser.oauthId())
			.orElseGet(() -> userRepository.save(UserEntity.from(oAuthUser).toModel()));
	}

	@Transactional
	@Override
	public User update(Long userId, String nickName, MultipartFile profileImage) throws IOException {
		User user = findByUserId(userId);
		if (!profileImage.isEmpty()) {
			String profileImgUrl = upload(profileImage, user);
			user = user.changeImgUrl(profileImgUrl);
		}
		if (hasText(nickName)) {
			user = user.changeNickName(nickName);
		}
		return userRepository.save(user);
	}

	@Override
	public String upload(MultipartFile profileImage, User user) throws IOException {
		if (!profileImage.isEmpty()) {
			FileCreate fileCreate = s3Service.upload(profileImage);
			return fileService.upload(fileCreate, user);
		}
		return null;
	}

	@Override
	public User findByUserId(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Transactional
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByOauthId(String oauthId) {
		return userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
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
	public User findByUserIdAndOnboarding(Long userId) {
		return null;
	}
}
