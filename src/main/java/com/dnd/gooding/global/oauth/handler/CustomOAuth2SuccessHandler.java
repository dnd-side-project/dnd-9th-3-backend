package com.dnd.gooding.global.oauth.handler;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.dnd.gooding.global.oauth.dto.CustomOAuth2User;
import com.dnd.gooding.global.oauth.repository.HttpCookieOAuthAuthorizationRequestRepository;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.token.service.TokenService;
import com.dnd.gooding.global.util.CookieUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final TokenService tokenService;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws ServletException, IOException {
		if (authentication.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
			Tokens tokens = tokenService.createTokens(customOAuth2User.getUserInfo());
			String targetUrl = determineTargetUrl(request, tokens.accessToken());
			setRefreshTokenInCookie(response, tokens.refreshToken());
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

	private String determineTargetUrl(HttpServletRequest request, String accessToken) {
		String targetUrl = CookieUtil.getCookie(request,
				HttpCookieOAuthAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue)
			.map(cookie -> URLDecoder.decode(cookie, UTF_8))
			.map(this::encodeKoreanCharacters)
			.orElse(getDefaultTargetUrl());

		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("accessToken", accessToken)
			.build().toUriString();
	}

	private String encodeKoreanCharacters(String url) {
		Pattern koreanPattern = Pattern.compile("[가-힣]+");
		Matcher matcher = koreanPattern.matcher(url);
		StringBuilder encodedUrl = new StringBuilder();

		while (matcher.find()) {
			String koreanText = matcher.group();
			// 한글 부분만 인코딩
			String encodedKoreanText = URLEncoder.encode(koreanText, UTF_8);
			// 인코딩된 한글로 치환
			matcher.appendReplacement(encodedUrl, encodedKoreanText);
		}
		matcher.appendTail(encodedUrl);

		return encodedUrl.toString();
	}

	private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
		ResponseCookie token = ResponseCookie.from("refreshToken", refreshToken)
			.path(getDefaultTargetUrl())
			.httpOnly(true)
			.sameSite("None")
			.secure(true)
			.maxAge(tokenService.getRefreshTokenExpirySeconds())
			.build();

		response.addHeader("Set-Cookie", token.toString());
	}
}
