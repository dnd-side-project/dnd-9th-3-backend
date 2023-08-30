package com.dnd.gooding.domain.user.domain;

import com.dnd.gooding.global.oauth.domain.OAuthUser;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

	private final Long id;
	private final String nickname;
	private final String profileImgUrl;
	private final String provider;
	private final String oauthId;
	private final String onboardYn;

	@Builder
	public User(Long id, String nickname, String profileImgUrl,
		String provider, String oauthId, String onboardYn) {
		this.id = id;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
		this.provider = provider;
		this.oauthId = oauthId;
		this.onboardYn = onboardYn;
	}

	public static User from(OAuthUser oAuthUser) {
		return User.builder()
			.nickname(oAuthUser.nickname())
			.profileImgUrl(oAuthUser.profileImgUrl())
			.provider(oAuthUser.provider())
			.oauthId(oAuthUser.oauthId())
			.build();
	}

	public User nickNameUpdate(String nickname) {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.build();
	}

	public User profileImageUrlUpdate(String profileImgUrl) {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.build();
	}
}
