package com.dnd.gooding.domain.user.controller;

import java.io.IOException;

import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.controller.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.user.controller.response.UserResponse;
import com.dnd.gooding.domain.user.controller.port.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/user")
@Builder
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "oauth 아이디로 사용자를 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/{oauthId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getUser(
		@Parameter(description = "oauth 아이디") @PathVariable String oauthId) {
		User user = userService.findByOauthId(oauthId);
		return ResponseEntity
			.ok()
			.body(UserResponse.from(user));
	}

	@Operation(summary = "사용자 정보를 업데이트한다.",
		responses = {
			@ApiResponse(responseCode = "204", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PatchMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UserResponse> update(
		@PathVariable("userId") Long userId,
		@RequestPart("nickName") String nickName,
		@RequestPart("profileImage") MultipartFile profileImage) throws IOException {
		User user = userService.update(userId, nickName, profileImage);
		return ResponseEntity
			.ok()
			.body(UserResponse.from(user));
	}

	@Operation(summary = "사용자를 삭제한다.",
		responses = {
			@ApiResponse(responseCode = "204", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity<Void> delete(
		@PathVariable("userId") Long userId,
		@CookieValue("refreshToken") String refreshToken) {
		userService.delete(userId, refreshToken);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
