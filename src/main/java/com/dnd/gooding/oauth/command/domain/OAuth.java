package com.dnd.gooding.oauth.command.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth")
public class OAuth {

	@Id
	private String oAuthId;
	private String imageUrl;
	private String provider;

	protected OAuth() {
	}

	public OAuth(String oAuthId, String imageUrl, String provider) {
		this.oAuthId = oAuthId;
		this.imageUrl = imageUrl;
		this.provider = provider;
	}

	public String getoAuthId() {
		return oAuthId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getProvider() {
		return provider;
	}
}
