package com.dnd.gooding.domain.user.controller.response;

import static java.util.stream.Collectors.*;

import java.util.List;

import com.dnd.gooding.domain.onboard.controller.response.OnboardResponse;
import com.dnd.gooding.domain.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 프로필")
@Getter
@Builder
public class UserResponse {
	@Schema(description = "사용자 ID")
	private Long id;
	@Schema(description = "사용자 닉네임")
	private String nickname;
	@Schema(description = "사용자 프로필 이미지")
	private String profileImgUrl;
	@Schema(description = "온보딩 여부")
	private String onboardYn;
	private List<OnboardResponse> onboards;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.profileImgUrl(user.getProfileImgUrl())
			.onboardYn(user.getOnboardYn())
			.onboards(user.getOnboards().stream()
				.map(OnboardResponse::from)
				.collect(toList()))
			.build();
	}
}
