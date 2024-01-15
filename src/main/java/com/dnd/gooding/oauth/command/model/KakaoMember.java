package com.dnd.gooding.oauth.command.model;

import java.util.Map;

public class KakaoMember {

	private String oauthId;
	private String name;
	private String imageUrl;
	private String provider;

	public KakaoMember(String oauthId, Map<String, String> properties) {
		this.oauthId = oauthId;
		this.name = properties.get("nickname").replace("\"", "");
		this.imageUrl = properties.get("profile_image").replace("\"", "");
		this.provider = "kakao";
	}

	public String getOauthId() {
		return oauthId;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getProvider() {
		return provider;
	}
}
