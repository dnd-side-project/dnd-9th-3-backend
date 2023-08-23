package com.dnd.gooding.domain.onboarding.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OnboardingRequest {
	private String nickName;
	private List<String> interestCodes;
}
