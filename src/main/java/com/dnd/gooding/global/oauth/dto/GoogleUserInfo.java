package com.dnd.gooding.global.oauth.dto;

public record GoogleUserInfo(
	String id,
	String email,
	String picture
) {
}
