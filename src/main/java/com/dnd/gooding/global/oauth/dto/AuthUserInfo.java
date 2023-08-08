package com.dnd.gooding.global.oauth.dto;

public record AuthUserInfo(
	Long id,
	String nickname,
	String role,
	String oauthId) {
}
