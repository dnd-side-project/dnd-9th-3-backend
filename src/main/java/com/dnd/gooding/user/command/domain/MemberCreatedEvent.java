package com.dnd.gooding.user.command.domain;

import com.dnd.gooding.common.event.Event;
import com.dnd.gooding.oauth.command.domain.OAuthId;

public class MemberCreatedEvent extends Event {

	private String email;
	private OAuthId oAuthId;

	private MemberCreatedEvent() {
	}

	public MemberCreatedEvent(String email, OAuthId oAuthId) {
		super();
		this.email = email;
		this.oAuthId = oAuthId;
	}

	public String getEmail() {
		return email;
	}

	public OAuthId getoAuthId() {
		return oAuthId;
	}
}
