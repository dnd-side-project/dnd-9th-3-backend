package com.dnd.gooding.domain.user.controller;

import com.dnd.gooding.global.common.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dnd.gooding.domain.user.dto.response.UserProfileResponse;
import com.dnd.gooding.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/user")
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
	public ResponseEntity<UserProfileResponse> getUser(
			@Parameter(description = "oauth 아이디") @PathVariable String oauthId) {
		return ResponseEntity
			.ok()
			.body(userService.getByOauthId(oauthId));
	}

	@Operation(summary = "사용자 정보를 업데이트한다.",
			responses = {
					@ApiResponse(responseCode = "204", description = "정상처리"),
					@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
							content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@PatchMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(
			@PathVariable("userId") Long userId,
			@RequestPart("nickName") String nickName,
			@RequestPart("profileImage") MultipartFile profileImage) throws IOException {
		userService.update(userId, nickName, profileImage);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@Operation(summary = "사용자를 삭제한다.",
			responses = {
					@ApiResponse(responseCode = "204", description = "정상처리"),
					@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
							content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity<Void> delete(
			@PathVariable("userId") Long userId) {
		userService.delete(userId, "");
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
