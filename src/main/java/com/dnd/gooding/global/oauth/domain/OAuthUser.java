package com.dnd.gooding.global.oauth.domain;

import lombok.Builder;

@Builder
public record OAuthUser(
	Long id,
	String nickname,
	String profileImgUrl,
	String provider,
	String oauthId
) {
}
