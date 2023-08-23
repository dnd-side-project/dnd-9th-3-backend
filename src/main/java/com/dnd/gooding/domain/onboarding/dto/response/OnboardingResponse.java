package com.dnd.gooding.domain.onboarding.dto.response;

import com.dnd.gooding.domain.onboarding.model.Onboarding;
import com.dnd.gooding.global.common.model.InterestType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "관심사 정보")
public class OnboardingResponse {
    private Long id;
    private InterestType interestType;
    public OnboardingResponse(Onboarding onboarding) {
        this.id = onboarding.getId();
        this.interestType = onboarding.getInterestType();
    }
    public static OnboardingResponse from(Onboarding onboarding) {
        return OnboardingResponse.builder()
                .id(onboarding.getId())
                .interestType(onboarding.getInterestType())
                .build();
    }
}

