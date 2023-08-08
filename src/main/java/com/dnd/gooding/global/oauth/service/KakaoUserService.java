package com.dnd.gooding.global.oauth.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dnd.gooding.domain.user.service.UserService;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.KakaoUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;
import com.dnd.gooding.global.oauth.dto.response.OAuthResponse;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.token.service.TokenService;
import com.dnd.gooding.global.util.CookieUtil;

@Service
public class KakaoUserService {

	private final UserService userService;
	private final TokenService tokenService;
	private final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
	public KakaoUserService(
		UserService userService,
		TokenService tokenService) {
		this.userService = userService;
		this.tokenService = tokenService;
	}

	public OAuthResponse getAccessToken(
		HttpServletResponse response,
		String accessToken) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity request = new HttpEntity(headers);

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(USER_INFO_URL);
		ResponseEntity<KakaoUserInfo> responseEntity = restTemplate.exchange(
			uriComponentsBuilder.toUriString(),
			HttpMethod.GET,
			request,
			KakaoUserInfo.class
		);

		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			AuthUserInfo user = saveUser(responseEntity.getBody());
			Tokens tokens = tokenService.createTokens(user);
			CookieUtil.addCookie(response, "refreshToken", tokens.refreshToken(), 180);
			return new OAuthResponse(tokens.accessToken(), user.oauthId());
		}
		return null;
	}

	@Transactional
	public AuthUserInfo saveUser(KakaoUserInfo kakaoUserInfo) {
		OAuthUserInfo oAuthUser = OAuthUserInfo.builder()
			.oauthId(kakaoUserInfo.id())
			.nickname(kakaoUserInfo.properties().get("nickname").replace("\"", ""))
			.profileImgUrl(kakaoUserInfo.properties().get("profile_image").replace("\"", ""))
			.provider("kakao")
			.build();
		return userService.getOrRegisterUser(oAuthUser);
	}
}
