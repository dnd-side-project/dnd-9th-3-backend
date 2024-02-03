package com.dnd.gooding.oauth.command.domain;

public class MemberCreateEvent {

	private String email;
	private OAuthId oAuthId;

	private MemberCreateEvent() {
	}

	public MemberCreateEvent(String email, OAuthId oAuthId) {
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
