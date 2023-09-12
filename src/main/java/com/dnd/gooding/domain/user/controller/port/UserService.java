package com.dnd.gooding.domain.user.controller.port;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.oauth.domain.OAuthUser;

public interface UserService {
	User create(OAuthUser oAuthUser);
	User update(Long userId, String nickName, MultipartFile profileImage) throws IOException;
	String upload(MultipartFile profileImage, User user) throws IOException;
	User findByOauthId(String oauthId);
	User findByUserId(Long userId);
	User save(User user);
	void delete(Long userId, String refreshToken);
	User findByUserIdAndOnboarding(Long userId);
}
