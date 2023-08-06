package com.dnd.gooding.global.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.global.oauth.service.GoogleUserService;
import com.dnd.gooding.global.oauth.service.KakaoUserService;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.token.dto.response.TokenResponse;
import com.dnd.gooding.global.util.CookieUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

	private final KakaoUserService kakaoUserService;
	private final GoogleUserService googleUserService;

	@Operation(summary = "카카오로 로그인한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
	})
	@SecurityRequirements()
	@GetMapping(value = "/kakao", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponse> kakao(
		HttpServletResponse response,
		@Parameter(description = "카카오에서 인가받은 액세스 토큰") @RequestParam String accessToken) {
		Tokens tokens = kakaoUserService.getAccessToken(accessToken);
		CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), 180);
		return ResponseEntity
			.ok()
			.body(new TokenResponse(tokens.accessToken()));
	}

	@Operation(summary = "구글로 로그인한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
		})
	@SecurityRequirements()
	@GetMapping(value = "/google", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponse> google(
		HttpServletResponse response,
		@Parameter(description = "구글에서 인가받은 액세스 토큰") @RequestParam String accessToken) {
		Tokens tokens = googleUserService.getAccessToken(accessToken);
		CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), 180);
		return ResponseEntity
			.ok()
			.body(new TokenResponse(tokens.accessToken()));
	}
}
