package com.dnd.gooding.oauth.command.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "oauth")
public class OAuth {

	@EmbeddedId
	private OAuthId oAuthId;
	private String imageUrl;
	private String provider;

	protected OAuth() {
	}

	public OAuth(OAuthId oAuthId, String imageUrl, String provider) {
		this.oAuthId = oAuthId;
		this.imageUrl = imageUrl;
		this.provider = provider;
	}

	public OAuthId getoAuthId() {
		return oAuthId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getProvider() {
		return provider;
	}
}
