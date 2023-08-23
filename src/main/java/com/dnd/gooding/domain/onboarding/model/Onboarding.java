package com.dnd.gooding.domain.onboarding.model;

import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.converter.InterestConverter;
import com.dnd.gooding.global.common.model.InterestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "onboarding")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Onboarding {

    @Id
    @Column(name = "onboarding_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "interest_type")
    @Convert(converter = InterestConverter.class)
    private InterestType interestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getOnboardings().add(this);
    }

    public static Onboarding from(User user, InterestType interestType) {
        Onboarding onboarding = new Onboarding();
        onboarding.interestType = interestType;
        onboarding.user = user;
        onboarding.setUser(user);
        return onboarding;
    }
}
