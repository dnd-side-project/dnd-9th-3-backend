package com.dnd.gooding.global.token.controller;

import static org.springframework.http.HttpHeaders.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.global.common.controller.response.ErrorResponse;
import com.dnd.gooding.global.token.dto.response.TokenResponse;
import com.dnd.gooding.global.token.service.TokenService;
import com.dnd.gooding.global.util.CookieUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Token", description = "토큰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tokens")
public class TokenController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final TokenService tokenService;

	@Operation(summary = "임시 토큰을 발급 받는다.")
	@SecurityRequirements()
	@GetMapping(value = "/temporary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponse> tempAccessToken() {
		String tempAccessToken = tokenService.createAccessToken(1L, "ROLE_USER");
		logger.info("[TokenController] tempAccessToken : " + tempAccessToken);
		return ResponseEntity
			.ok()
			.body(new TokenResponse(tempAccessToken));

	}

	@Operation(summary = "액세스 토큰을 재발급받는다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "401", description = "유효하지 않은 리프레쉬 토큰입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/reissue", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponse> refreshAccessToken(
		@CookieValue("refreshToken") String refreshToken) {
		String accessToken = tokenService.getAccessTokensByRefreshToken(refreshToken);
		return ResponseEntity
			.ok()
			.body(new TokenResponse(accessToken));
	}

	@Operation(summary = "리프레시 토큰과 액세스 토큰을 삭제한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "401", description = "쿠키를 찾을 수 없습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> expireRefreshToken(
		@CookieValue("refreshToken") String refreshToken) {
		tokenService.deleteRefreshToken(refreshToken);
		ResponseCookie emptyCookie = CookieUtil.getEmptyCookie("refreshToken");
		return ResponseEntity
			.noContent()
			.header(SET_COOKIE, emptyCookie.toString()).build();
	}
}
