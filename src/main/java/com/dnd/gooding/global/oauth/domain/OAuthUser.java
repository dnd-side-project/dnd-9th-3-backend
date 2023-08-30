package com.dnd.gooding.global.oauth.domain;

import lombok.Builder;

@Builder
public record OAuthUser(
	String nickname,
	String profileImgUrl,
	String provider,
	String oauthId
) {
}
