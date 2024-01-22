package com.dnd.gooding.user.query;

import javax.persistence.*;

import com.dnd.gooding.common.jpa.EmailSetConverter;
import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.InterestSet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class MemberData {

	@Id
	@Column(name = "member_id")
	private String id;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@Column(name = "emails")
	@Convert(converter = EmailSetConverter.class)
	private EmailSet emailSet;
	@JsonIgnore
	@Column(name = "interests")
	@Convert(converter = InterestConverter.class)
	private InterestSet interestSet;
	@Transient
	private List<Email> emails = new ArrayList<>();
	@Transient
	private List<Interest> interests = new ArrayList<>();

	protected MemberData() {
	}

	public MemberData(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EmailSet getEmailSet() {
		return emailSet;
	}

	public InterestSet getInterestSet() {
		return interestSet;
	}

	public List<Email> getEmails() {
		if (emailSet != null && emailSet.getEmails() != null) {
			return emailSet.getEmails().stream().toList();
		} else {
			return emails;
		}
	}

	public List<Interest> getInterests() {
		if (interestSet != null && interestSet.getInterests() != null) {
			return interestSet.getInterests().stream().toList();
		}
		return interests;
	}
}
