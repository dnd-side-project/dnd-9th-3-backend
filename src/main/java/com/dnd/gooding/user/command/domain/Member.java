package com.dnd.gooding.user.command.domain;

import java.util.Random;
import java.util.Set;

import javax.persistence.*;

import com.dnd.gooding.common.jpa.EmailSetConverter;
import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.InterestSet;
import com.dnd.gooding.oauth.command.domain.OAuthId;

@Entity
@Table(name = "member")
@Access(AccessType.FIELD)
public class Member {

	@EmbeddedId
	private MemberId id;
	private String name;
	private Password password;
	@Column(name = "emails")
	@Convert(converter = EmailSetConverter.class)
	private EmailSet emails;
	@Column(name = "interests")
	@Convert(converter = InterestConverter.class)
	private InterestSet interests;
	private String userRole;
	@Embedded
	private OAuthId oAuthId;

	protected Member() {
	}

	public Member(MemberId id, String name, EmailSet emails,
		InterestSet interests, String userRole, OAuthId oAuthId) {
		this.id = id;
		this.name = name;
		this.emails = emails;
		this.interests = interests;
		this.userRole = userRole;
		this.oAuthId = oAuthId;
	}

	public void initializePassword() {
		String newPassword = generateRandomPassword();
		this.password = new Password(newPassword);
	}

	private String generateRandomPassword() {
		Random random = new Random();
		int number = random.nextInt();
		return Integer.toHexString(number);
	}

	public void changeEmails(Set<Email> emails) {
		this.emails = new EmailSet(emails);
	}

	public void changeInterests(Set<Interest> interests) {
		this.interests = new InterestSet(interests);
	}

	public void changePassword(String oldPw, String newPw) {
		if (!password.match(oldPw)) {
			throw new IdPasswordNotMatchingException();
		}
		this.password = new Password(newPw);
	}

	public MemberId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Password getPassword() {
		return password;
	}

	public EmailSet getEmails() {
		return emails;
	}

	public InterestSet getInterests() {
		return interests;
	}

	public String getUserRole() {
		return userRole;
	}

	public OAuthId getoAuthId() {
		return oAuthId;
	}
}
