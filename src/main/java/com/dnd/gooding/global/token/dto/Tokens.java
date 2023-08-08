package com.dnd.gooding.global.token.dto;

import com.dnd.gooding.global.token.dto.response.TokenResponse;

public record Tokens(
	String accessToken,
	String refreshToken
) {
}
