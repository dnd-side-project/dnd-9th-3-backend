package com.dnd.gooding.global.oauth.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dnd.gooding.domain.user.controller.port.UserService;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.enums.ProviderType;
import com.dnd.gooding.global.oauth.domain.AuthUser;
import com.dnd.gooding.global.oauth.domain.KakaoUser;
import com.dnd.gooding.global.oauth.domain.OAuthUser;
import com.dnd.gooding.global.oauth.controller.response.OAuthResponse;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.token.service.TokenService;
import com.dnd.gooding.global.util.CookieUtil;

@Service
public class KakaoUserService {

	private final UserService userService;
	private final TokenService tokenService;
	private final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
	public KakaoUserService(UserService userService, TokenService tokenService) {
		this.userService = userService;
		this.tokenService = tokenService;
	}

	public OAuthResponse getAccessToken(HttpServletResponse response, String accessToken) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity request = new HttpEntity(headers);

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(USER_INFO_URL);
		ResponseEntity<KakaoUser> responseEntity = restTemplate.exchange(
			uriComponentsBuilder.toUriString(),
			HttpMethod.GET,
			request,
			KakaoUser.class
		);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			AuthUser authUser = create(responseEntity.getBody());
			Tokens tokens = tokenService.createTokens(authUser);
			CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), 180);
			return new OAuthResponse(tokens.accessToken(), authUser.getOauthId());
		}
		return null;
	}

	public AuthUser create(KakaoUser kakaoUser) {
		OAuthUser oAuthUser = OAuthUser.builder()
			.oauthId(kakaoUser.id())
			.nickname(kakaoUser.properties().get("nickname").replace("\"", ""))
			.profileImgUrl(kakaoUser.properties().get("profile_image").replace("\"", ""))
			.provider(ProviderType.kakao.name())
			.build();
		User user = userService.create(oAuthUser);
		return AuthUser.from(user);
	}
}
