package com.dnd.gooding.global.oauth.dto;

public record GoogleUserInfo(
	String sub,
	String email,
	String name,
	String picture
) {
}
