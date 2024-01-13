package com.dnd.gooding.user.command.domain.oauthmember;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OAuthMemberId implements Serializable {

	@Column(name = "oauth_id")
	private String id;

	protected OAuthMemberId() {
	}

	public OAuthMemberId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OAuthMemberId that = (OAuthMemberId)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
