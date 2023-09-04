package com.dnd.gooding.domain.user.domain;

import java.util.ArrayList;
import java.util.List;

import com.dnd.gooding.domain.onboard.domain.Onboard;

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
	private final List<Onboard> onboards;

	@Builder
	public User(Long id, String nickname, String profileImgUrl,
		String provider, String oauthId, String onboardYn, List<Onboard> onboards) {
		this.id = id;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
		this.provider = provider;
		this.oauthId = oauthId;
		this.onboardYn = onboardYn;
		this.onboards = onboards;
	}

	public User changeOnboards(List<Onboard> onboards) {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.onboards(onboards)
			.build();
	}

	public User changeNickName(String nickname) {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.onboards(onboards)
			.build();
	}

	public User changeImgUrl(String profileImgUrl) {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.onboards(onboards)
			.build();
	}

	public User changeOnboardYn(String onboardYn) {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.onboards(onboards)
			.build();
	}
}
