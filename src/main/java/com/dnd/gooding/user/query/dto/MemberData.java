package com.dnd.gooding.user.query.dto;

import com.dnd.gooding.common.jpa.EmailSetConverter;
import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.EmailSet;
import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.InterestSet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Getter
@Entity
@Immutable
@Table(name = "member")
@Subselect(
        "select m.member_id, m.name, m.emails, m.interests, m.oauth_id, o.image_url "
                + "from member m "
                + "left join oauth o "
                + "on m.oauth_id = o.oauth_id")
@Synchronize({"member", "oauth"})
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

    @Transient private List<Email> emails = new ArrayList<>();
    @Transient private List<Interest> interests = new ArrayList<>();

    @Column(name = "oauth_id")
    private String oAuthId;

    @Column(name = "image_url")
    private String imageUrl;

    protected MemberData() {}

    @Builder
    public MemberData(
            String id,
            String name,
            List<Email> emails,
            List<Interest> interests,
            String oAuthId,
            String imageUrl) {
        this.id = id;
        this.name = name;
        this.emails = emails;
        this.interests = interests;
        this.oAuthId = oAuthId;
        this.imageUrl = imageUrl;
    }

    public List<Email> getEmails() {
        if (emailSet != null && emailSet.getEmails() != null) {
            return new ArrayList<>(emailSet.getEmails());
        } else {
            return emails;
        }
    }

    public List<Interest> getInterests() {
        if (interestSet != null && interestSet.getInterests() != null) {
            return new ArrayList<>(interestSet.getInterests());
        }
        return interests;
    }
}
