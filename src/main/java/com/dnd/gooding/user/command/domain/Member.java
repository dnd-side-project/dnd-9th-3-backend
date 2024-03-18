package com.dnd.gooding.user.command.domain;

import com.dnd.gooding.common.jpa.EmailSetConverter;
import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.InterestSet;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.exception.IdPasswordNotMatchingException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "member")
@Access(AccessType.FIELD)
public class Member {

    @EmbeddedId private MemberId id;
    private String name;
    private Password password;

    @Column(name = "emails")
    @Convert(converter = EmailSetConverter.class)
    private EmailSet emails;

    @Column(name = "interests")
    @Convert(converter = InterestConverter.class)
    private InterestSet interests;

    private String userRole;
    @Embedded private OAuthId oAuthId;

    protected Member() {}

    @Builder
    public Member(
            MemberId id,
            String name,
            EmailSet emails,
            InterestSet interests,
            String userRole,
            OAuthId oAuthId) {
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
        Random random = new SecureRandom();
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

    public void changeName(String name) {
        this.name = name;
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
