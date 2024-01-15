package com.dnd.gooding.user.command.application;

import java.util.HashSet;
import java.util.Set;

import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.Interest;

public class MemberRequest {

	private String memberId;
	private Set<Email> emails = new HashSet<>();
	private Set<Interest> interests = new HashSet<>();
	private String name;
	private String oAuthId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getoAuthId() {
		return oAuthId;
	}

	public void setoAuthId(String oAuthId) {
		this.oAuthId = oAuthId;
	}
}
