package com.dnd.gooding.user.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.user.command.application.oauthmember.KakaoLoginService;

@RestController
@RequestMapping("/api/v1/auth")
public class SocialLoginController {

	private KakaoLoginService kakaoLoginService;

	public SocialLoginController(KakaoLoginService kakaoLoginService) {
		this.kakaoLoginService = kakaoLoginService;
	}

	@GetMapping
	public ResponseEntity<Token> kakao(
		@RequestParam String code) {
		return ResponseEntity
			.ok()
			.body(kakaoLoginService.getAccessToken(code));
	}
}
