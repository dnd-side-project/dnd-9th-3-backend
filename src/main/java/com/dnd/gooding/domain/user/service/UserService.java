package com.dnd.gooding.domain.user.service;

import com.dnd.gooding.domain.user.dto.request.UpdateUserRequest;
import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

public interface UserService {
	/* 회원 등록 */
	AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo);

	/* 회원 프로필 조회 */
	UserProfileResponse getById(Long userId);

	User save(User user);

	/* 회원 프로필 수정 */
	UserProfileResponse updateUserProfile(UpdateUserRequest updateUserRequest, Long userId);

	/* 회원 탈퇴 */
	void delete(Long userId, String refreshToken);
}
