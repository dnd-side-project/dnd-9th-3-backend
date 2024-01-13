package com.dnd.gooding.common.model;

public class Token {

	private String oauthId;
	private String accessToken;
	private String refreshToken;

	public Token(String oauthId, String accessToken, String refreshToken) {
		this.oauthId = oauthId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getOauthId() {
		return oauthId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
