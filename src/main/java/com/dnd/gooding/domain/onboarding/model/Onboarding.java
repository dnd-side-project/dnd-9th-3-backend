package com.dnd.gooding.domain.onboarding.model;

import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.global.common.converter.InterestConverter;
import com.dnd.gooding.global.common.model.BaseEntity;
import com.dnd.gooding.global.common.model.InterestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "onboarding")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Onboarding extends BaseEntity {

    @Id
    @Column(name = "onboarding_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest_type")
    @Convert(converter = InterestConverter.class)
    private InterestType interestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Onboarding from(User user, String interestCode) {
        Onboarding onboarding = new Onboarding();
        onboarding.interestType = InterestType.ofInterestCode(interestCode);
        onboarding.user = user;
        return onboarding;
    }
}
