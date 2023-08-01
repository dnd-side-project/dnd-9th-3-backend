package com.dnd.gooding.global.oauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.global.oauth.service.OAuth2UserService;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.util.CookieUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping
public class OAuthController {

	private final OAuth2UserService oAuth2UserService;

	@GetMapping("/oauth/kakao")
	public String kakao(
		HttpServletResponse response,
		@RequestParam String code) {
		Tokens tokens = oAuth2UserService.getKakaoAccessToken(code);
		CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), 180);
		return tokens.accessToken();
	}
}
