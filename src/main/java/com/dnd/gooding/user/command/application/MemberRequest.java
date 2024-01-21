package com.dnd.gooding.user.command.application;

import java.util.HashSet;
import java.util.Set;

import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.Interest;

public class MemberRequest {

	private String id;
	private String name;
	private String password;
	private Set<Email> emailSet = new HashSet<>();
	private Set<Interest> interestSet = new HashSet<>();

	public MemberRequest() {
	}

	public MemberRequest(String id, String name, String password,
		Set<Email> emailSet, Set<Interest> interestSet) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.emailSet = emailSet;
		this.interestSet = interestSet;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Set<Email> getEmailSet() {
		return emailSet;
	}

	public Set<Interest> getInterestSet() {
		return interestSet;
	}
}
