package com.dnd.gooding.user.command.domain;

import java.util.Random;
import java.util.Set;

import javax.persistence.*;

import com.dnd.gooding.common.jpa.EmailSetConverter;
import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.InterestSet;
import com.dnd.gooding.oauth.command.domain.OAuth;

@Entity
@Table(name = "member")
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
	@OneToOne
	private OAuth oAuth;

	protected Member() {
	}

	public Member(MemberId id, String name) {
		this.id = id;
		this.name = name;
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

	public OAuth getoAuth() {
		return oAuth;
	}
}
