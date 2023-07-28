package com.dnd.gooding.global.oauth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dnd.gooding.domain.user.service.UserService;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.CustomOAuth2User;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String providerName = userRequest.getClientRegistration().getRegistrationId();
		OAuthUserInfo oAuthUserInfo = extractUserInfoFromOAuth2User(oAuth2User, providerName);
		AuthUserInfo user = userService.getOrRegisterUser(oAuthUserInfo);
		return new CustomOAuth2User(user, oAuth2User.getAttributes());
	}

	private OAuthUserInfo extractUserInfoFromOAuth2User(OAuth2User oAuth2User, String providerName) {
		return OAuthProvider
			.getOAuthProviderByName(providerName)
			.toUserInfo(oAuth2User);
	}
}
