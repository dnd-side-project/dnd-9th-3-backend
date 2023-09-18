package com.dnd.gooding.domain.onboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.domain.onboard.controller.port.OnboardService;
import com.dnd.gooding.domain.onboard.controller.request.OnboardRequest;
import com.dnd.gooding.domain.user.controller.response.UserResponse;
import com.dnd.gooding.global.common.controller.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "Onboard", description = " 온보딩 API")
@RestController
@RequestMapping("/api/v1/onboard")
@Builder
@RequiredArgsConstructor
public class OnboardController {

	private final OnboardService onboardService;

	@Operation(summary = "온보딩 내용을 저장한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/update/{userId}",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(
		@PathVariable("userId") Long userId,
		OnboardRequest onboardRequest) {
		onboardService.create(userId, onboardRequest.getNickName(), onboardRequest.getInterestCodes());
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
