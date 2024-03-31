package com.dnd.gooding.oauth.command.domain.event;

public class LoginSuccessEvent {

	private String email;

	public LoginSuccessEvent(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
