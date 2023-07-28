package com.dnd.gooding.global.oauth.service;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

	GOOGLE("google") {
		@Override
		public OAuthUserInfo toUserInfo(OAuth2User oAuth2User) {
			Map<String, Object> attributes = oAuth2User.getAttributes();
			return OAuthUserInfo.builder()
				.provider(GOOGLE.name)
				.oauthId(String.valueOf(oAuth2User.getName()))
				.nickname(String.valueOf(attributes.get("name")))
				.profileImgUrl(String.valueOf(attributes.get("picture")))
				.build();
		}
	};

	private static final Map<String, OAuthProvider> PROVIDERS =
		Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(OAuthProvider::getName, Function.identity())));
	private final String name;

	public static OAuthProvider getOAuthProviderByName(String providerName) {
		if (!PROVIDERS.containsKey(providerName)) {
			throw new IllegalArgumentException("지원하지 않는 로그인입니다.");
		}
		return PROVIDERS.get(providerName);
	}

	public abstract OAuthUserInfo toUserInfo(OAuth2User oAuth2User);
}
