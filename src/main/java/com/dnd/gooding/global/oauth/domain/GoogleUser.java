package com.dnd.gooding.global.oauth.domain;

public record GoogleUser(
	String sub,
	String email,
	String name,
	String picture
) {
}
