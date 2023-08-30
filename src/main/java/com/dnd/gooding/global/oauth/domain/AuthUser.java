package com.dnd.gooding.global.oauth.domain;

import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.enums.RoleType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthUser {

	private final Long id;
	private final String nickname;
	private final String role;
	private final String oauthId;

	@Builder
	public AuthUser(Long id, String nickname, String role, String oauthId) {
		this.id = id;
		this.nickname = nickname;
		this.role = role;
		this.oauthId = oauthId;
	}

	public static AuthUser from(User user) {
		return AuthUser.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.role(RoleType.ROLE_USER.name())
			.oauthId(user.getOauthId())
			.build();
	}
}
