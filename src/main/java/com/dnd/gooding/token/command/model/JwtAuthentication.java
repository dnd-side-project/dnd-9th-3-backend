package com.dnd.gooding.token.command.model;

public class JwtAuthentication {

	private String memberId;
	private String accessToken;

	public JwtAuthentication(String memberId, String accessToken) {
		this.memberId = memberId;
		this.accessToken = accessToken;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
