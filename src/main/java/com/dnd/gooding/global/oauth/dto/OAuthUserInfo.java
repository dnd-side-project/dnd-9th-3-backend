package com.dnd.gooding.global.oauth.dto;

import lombok.Builder;

@Builder
public record OAuthUserInfo(
	String nickname,
	String profileImgUrl,
	String provider,
	String oauthId
) {
}
