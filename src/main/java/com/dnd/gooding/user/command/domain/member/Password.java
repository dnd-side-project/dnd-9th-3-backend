package com.dnd.gooding.user.command.domain.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Password implements Serializable {

	@Column(name = "password")
	private String value;

	protected Password() {
	}

	public Password(String value) {
		this.value = value;
	}

	public boolean match(String password) {
		return this.value.equals(password);
	}
}
