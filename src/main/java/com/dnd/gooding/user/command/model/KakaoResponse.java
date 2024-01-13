package com.dnd.gooding.user.command.model;

import java.util.Map;

public class KakaoResponse {

	private String id;
	private Map<String, String> properties;

	public KakaoResponse(String id, Map<String, String> properties) {
		this.id = id;
		this.properties = properties;
	}

	public String getId() {
		return id;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
