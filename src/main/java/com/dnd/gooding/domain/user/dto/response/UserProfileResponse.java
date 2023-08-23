package com.dnd.gooding.domain.user.dto.response;

import com.dnd.gooding.domain.onboarding.dto.response.OnboardingResponse;
import com.dnd.gooding.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Schema(description = "사용자 프로필")
@Getter
@Setter
public class UserProfileResponse {
	@Schema(description = "사용자 ID")
	private Long id;
	@Schema(description = "사용자 닉네임")
	private String nickname;
	@Schema(description = "사용자 프로필 이미지")
	private String profileImgUrl;
	@Schema(description = "온보딩 여부")
	private String onboardYn;
	private List<OnboardingResponse> onboardings;

	public static UserProfileResponse from(User user) {
		return UserProfileResponse.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.profileImgUrl(user.getProfileImgUrl())
			.onboardings(user.getOnboardings().stream()
					.map(OnboardingResponse::new)
					.collect(Collectors.toList()))
			.build();
	}
}
