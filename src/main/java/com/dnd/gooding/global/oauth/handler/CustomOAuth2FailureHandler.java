package com.dnd.gooding.global.oauth.handler;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.dnd.gooding.global.oauth.repository.HttpCookieOAuthAuthorizationRequestRepository;
import com.dnd.gooding.global.util.CookieUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final String DEFAULT_TARGET_URL = "/";
	private final HttpCookieOAuthAuthorizationRequestRepository httpCookieOAuthAuthorizationRequestRepository;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		String redirectUrl = CookieUtil.getCookie(request,
				HttpCookieOAuthAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue)
			.map(cookie -> URLDecoder.decode(cookie, UTF_8))
			.orElse(DEFAULT_TARGET_URL);

		String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
			.queryParam("error", specialCharactersRemove(exception.getMessage()))
			.build().toUriString();

		httpCookieOAuthAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	private String specialCharactersRemove(String errorMessage) {
		return errorMessage.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");
	}
}
