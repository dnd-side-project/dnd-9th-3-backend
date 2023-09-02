package com.dnd.gooding.domain.user.controller.port;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.user.infrastructure.UserEntity;
import com.dnd.gooding.global.oauth.domain.OAuthUser;

public interface UserService {
	UserEntity create(OAuthUser oAuthUser);
	UserEntity update(Long userId, String nickName, MultipartFile profileImage) throws IOException;
	UserEntity findByOauthId(String oauthId);
	UserEntity findByUserId(Long userId);
	UserEntity save(UserEntity userEntity);
	void delete(Long userId, String refreshToken);
	UserEntity findByUserIdAndOnboarding(Long userId);
}
