package com.dnd.gooding.global.oauth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰")
public record OAuthResponse(
	@Schema(description = "액세스 토큰")
	String accessToken,
	@Schema(description = "사용자 고유 아이디")
	String oauthId
) {
}
