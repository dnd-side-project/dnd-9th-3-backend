package com.dnd.gooding.domain.onboard.controller.response;

import com.dnd.gooding.domain.onboard.domain.Onboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "관심사 정보")
@Getter
@Builder
public class OnboardResponse {
	private Long id;
	private String interestCode;
	private String interestName;

	public static OnboardResponse from(Onboard onboard) {
		return OnboardResponse.builder()
			.id(onboard.getId())
			.interestCode(onboard.getInterestType().getInterestCode())
			.interestName(onboard.getInterestType().getInterestName())
			.build();
	}
}
