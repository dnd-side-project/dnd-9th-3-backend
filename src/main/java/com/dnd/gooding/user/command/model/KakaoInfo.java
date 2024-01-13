package com.dnd.gooding.user.command.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoInfo {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("id_token")
	private String idToken;
	@JsonProperty("expires_in")
	private int expiresIn;
	private String scope;
	@JsonProperty("refresh_token_expires_in")
	private int refreshTokenExpiresIn;

	public KakaoInfo(String accessToken, String tokenType, String refreshToken, String idToken, int expiresIn,
		String scope,
		int refreshTokenExpiresIn) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.refreshToken = refreshToken;
		this.idToken = idToken;
		this.expiresIn = expiresIn;
		this.scope = scope;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getIdToken() {
		return idToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public String getScope() {
		return scope;
	}

	public int getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}
}
