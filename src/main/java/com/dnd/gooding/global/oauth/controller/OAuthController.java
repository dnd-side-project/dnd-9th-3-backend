package com.dnd.gooding.global.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.global.oauth.dto.response.OAuthResponse;
import com.dnd.gooding.global.oauth.service.GoogleUserService;
import com.dnd.gooding.global.oauth.service.KakaoUserService;

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
	public ResponseEntity<OAuthResponse> kakao(
		HttpServletResponse response,
		@Parameter(description = "카카오에서 인가받은 액세스 토큰") @RequestParam String accessToken) {
		OAuthResponse oAuthResponse = kakaoUserService.getAccessToken(response, accessToken);
		return ResponseEntity
			.ok()
			.body(oAuthResponse);
	}

	@Operation(summary = "구글로 로그인한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
		})
	@SecurityRequirements()
	@GetMapping(value = "/google", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OAuthResponse> google(
		HttpServletResponse response,
		@Parameter(description = "구글에서 인가받은 ID 토큰") @RequestParam String idToken) {
		OAuthResponse oAuthResponse = googleUserService.getAccessToken(response, idToken);
		return ResponseEntity
			.ok()
			.body(oAuthResponse);
	}
}
