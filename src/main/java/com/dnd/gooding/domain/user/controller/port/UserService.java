package com.dnd.gooding.domain.user.controller.port;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
	AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo);
	UserProfileResponse getByOauthId(String oauthId);
	User findByUserId(Long userId);
	UserProfileResponse getByUserId(Long userId);
	User findByUserIdAndOnboarding(Long userId);
	User save(User user);
	void delete(Long userId, String refreshToken);
	void update(Long userId, String nickName, MultipartFile profileImage) throws IOException;
}
