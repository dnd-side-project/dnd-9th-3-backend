package com.dnd.gooding.user.query;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd.gooding.common.jpa.EmailSetConverter;
import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.InterestSet;

@Entity
@Table(name = "member")
public class MemberData {

	@Id
	@Column(name = "member_id")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "emails")
	@Convert(converter = EmailSetConverter.class)
	private EmailSet emailSet;
	@Column(name = "interests")
	@Convert(converter = InterestConverter.class)
	private InterestSet interestSet;
	private String userRole;
	@Column(name = "oauth_id")
	private String oauthId;

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

	public String getUserRole() {
		return userRole;
	}

	public String getOauthId() {
		return oauthId;
	}
}
