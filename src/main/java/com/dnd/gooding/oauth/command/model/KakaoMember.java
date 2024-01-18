package com.dnd.gooding.oauth.command.model;

import java.util.Map;

public class KakaoMember extends OAuthMember {

	private String oAuthId;
	private Map<String, String> properties;

	public KakaoMember(String oAuthId, Map<String, String> properties) {
		this.oAuthId = oAuthId;
		this.properties = properties;
	}

	@Override
	public String getImageUrl() {
		return properties.get("profile_image").replace("\"", "");
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getoAuthId() {
		return oAuthId;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
