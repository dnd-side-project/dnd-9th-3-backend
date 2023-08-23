package com.dnd.gooding.domain.onboarding.dto.response;

import com.dnd.gooding.domain.onboarding.model.Onboarding;
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
    private String interestCode;
    private String interestName;

    public OnboardingResponse(Onboarding onboarding) {
        this.id = onboarding.getId();
        this.interestCode = onboarding.getInterestType().getInterestCode();
        this.interestName = onboarding.getInterestType().getInterestName();
    }

    public static OnboardingResponse from(Onboarding onboarding) {
        return OnboardingResponse.builder()
                .id(onboarding.getId())
                .interestCode(onboarding.getInterestType().getInterestCode())
                .interestName(onboarding.getInterestType().getInterestName())
                .build();
    }
}

