package com.dnd.gooding.domain.onboard.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd.gooding.domain.onboard.domain.Onboard;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;
import com.dnd.gooding.global.common.converter.InterestConverter;
import com.dnd.gooding.global.common.enums.InterestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "onboarding")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardEntity {

    @Id
    @Column(name = "onboarding_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest_type")
    @Convert(converter = InterestConverter.class)
    private InterestType interestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static OnboardEntity from(Onboard onboard) {
        OnboardEntity onboardEntity = new OnboardEntity();
        onboardEntity.id = onboard.getId();
        onboardEntity.interestType = onboard.getInterestType();
        return onboardEntity;
    }

    public Onboard toModel() {
        return Onboard.builder()
            .id(id)
            .interestType(interestType)
            .build();
    }
}
