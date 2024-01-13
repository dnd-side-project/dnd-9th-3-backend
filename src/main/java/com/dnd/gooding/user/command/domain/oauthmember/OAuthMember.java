package com.dnd.gooding.user.command.domain.oauthmember;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

@Embeddable
public class OAuthMember {

	@EmbeddedId
	private OAuthMemberId oAuthMemberId;
	private String imageUrl;
	private String provider;

	protected OAuthMember() {
	}

	public OAuthMember(OAuthMemberId oAuthMemberId, String imageUrl, String provider) {
		this.oAuthMemberId = oAuthMemberId;
		this.imageUrl = imageUrl;
		this.provider = provider;
	}

	public OAuthMemberId getoAuthMemberId() {
		return oAuthMemberId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getProvider() {
		return provider;
	}
}
