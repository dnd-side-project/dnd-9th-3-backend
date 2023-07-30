package com.dnd.gooding.domain.user.controller.response;

import com.dnd.gooding.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "사용자 프로필")
public record UserProfileResponse(
	@Schema(description = "사용자 ID")
	Long id,
	@Schema(description = "사용자 닉네임")
	String nickname,
	@Schema(description = "사용자 프로필 이미지")
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
