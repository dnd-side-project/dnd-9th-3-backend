package com.dnd.gooding.domain.user.controller;

import com.dnd.gooding.global.common.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "사용자를 검색한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserProfileResponse> getById(
			@Parameter(description = "사용자 아이디") @PathVariable Long id) {
		return ResponseEntity
			.ok()
			.body(userService.getById(id));
	}
}
