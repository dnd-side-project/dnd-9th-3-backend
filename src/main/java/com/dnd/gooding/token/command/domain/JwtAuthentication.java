package com.dnd.gooding.token.command.domain;

public class JwtAuthentication {

	private String oauthId;
	private String accessToken;

	public JwtAuthentication(String oauthId, String accessToken) {
		this.oauthId = oauthId;
		this.accessToken = accessToken;
	}

	public String getOauthId() {
		return oauthId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
