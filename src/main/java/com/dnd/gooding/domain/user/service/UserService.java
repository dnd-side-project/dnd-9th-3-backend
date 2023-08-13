package com.dnd.gooding.domain.user.service;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
	/* 회원 등록 */
	AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo);
	/* 회원 프로필 조회 */
	UserProfileResponse getByOauthId(String oauthId);
	User save(User user);
	void delete(Long userId, String refreshToken);
	void update(Long userId, String nickName, MultipartFile profileImage) throws IOException;
}
