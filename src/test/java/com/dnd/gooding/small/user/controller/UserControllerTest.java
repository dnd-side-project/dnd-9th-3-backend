package com.dnd.gooding.small.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dnd.gooding.domain.user.controller.response.UserResponse;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.mock.TestContainer;

public class UserControllerTest {

	@Test
	void oauth_아이디로_사용자를_조회할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(0L)
			.nickname("haeyonghahn")
			.oauthId("2942669632")
			.profileImgUrl("http://k.kakaocdn.net/dn/bwXMb4/btsffv6Enze/CWxCrOgCvTAUz5FxUjWLUk/img_640x640.jpg")
			.provider("kakao")
			.onboards(new ArrayList<>())
			.build());

		// when
		ResponseEntity<UserResponse> result = testContainer.userController.getUser("2942669632");

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getId()).isEqualTo(1L);
		assertThat(result.getBody().getNickname()).isEqualTo("haeyonghahn");
		assertThat(result.getBody().getProfileImgUrl())
			.isEqualTo("http://k.kakaocdn.net/dn/bwXMb4/btsffv6Enze/CWxCrOgCvTAUz5FxUjWLUk/img_640x640.jpg");
	}

	@Test
	void 존재하지_않는_oauth_아이디로_api_호출할_경우_404_응답을_받는다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.userController.getUser("2942669632");
		}).isInstanceOf(UserNotFoundException.class);
	}
}
