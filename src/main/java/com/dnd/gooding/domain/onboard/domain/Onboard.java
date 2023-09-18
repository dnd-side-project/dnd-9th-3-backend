package com.dnd.gooding.domain.onboard.domain;

import com.dnd.gooding.domain.onboard.controller.response.OnboardResponse;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.enums.InterestType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Onboard {

	private final Long id;
	private final InterestType interestType;
	private final User user;

	@Builder
	public Onboard(Long id, InterestType interestType, User user) {
		this.id = id;
		this.interestType = interestType;
		this.user = user;
	}

	public static Onboard create(User user, String interestCode) {
		return Onboard.builder()
			.interestType(InterestType.ofInterestCode(interestCode))
			.user(user)
			.build();
	}

	public static OnboardResponse from(Onboard onboard) {
		return OnboardResponse.builder()
			.id(onboard.getId())
			.interestCode(onboard.getInterestType().getInterestCode())
			.interestName(onboard.getInterestType().getInterestName())
			.build();
	}
}
