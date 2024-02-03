package com.dnd.gooding.oauth.command.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dnd.gooding.common.event.Events;

@Entity
@Table(name = "oauth")
public class OAuth {

	@EmbeddedId
	private OAuthId oAuthId;
	private String imageUrl;
	private String provider;
	private String email;

	protected OAuth() {
	}

	public OAuth(OAuthId oAuthId, String imageUrl, String provider, String email) {
		this.oAuthId = oAuthId;
		this.imageUrl = imageUrl;
		this.provider = provider;
		this.email = email;
		Events.raise(new MemberCreateEvent(email, oAuthId));
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

	public String getEmail() {
		return email;
	}
}
