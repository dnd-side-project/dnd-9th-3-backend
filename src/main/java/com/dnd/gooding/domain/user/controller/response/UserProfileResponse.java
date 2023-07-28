package com.dnd.gooding.domain.user.controller.response;

import com.dnd.gooding.domain.user.model.User;

import lombok.Builder;

@Builder
public record UserProfileResponse(
	Long id,
	String nickname,
	String profileImgUrl
) {

	public static UserProfileResponse from(User user) {
		return UserProfileResponse.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.profileImgUrl(user.getProfileImgUrl())
			.build();
	}
}
