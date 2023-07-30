package com.dnd.gooding.global.token.api.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰")
public record TokenResponse(
	@Schema(description = "액세스 토큰")
	String accessToken
) {
}
