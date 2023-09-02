package com.dnd.gooding.domain.user.domain;

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
}
