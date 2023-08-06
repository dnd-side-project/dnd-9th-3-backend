package com.dnd.gooding.global.oauth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dnd.gooding.domain.user.service.UserService;
import com.dnd.gooding.global.oauth.dto.AuthUserInfo;
import com.dnd.gooding.global.oauth.dto.GoogleUserInfo;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;
import com.dnd.gooding.global.token.dto.Tokens;
import com.dnd.gooding.global.token.service.TokenService;

@Service
public class GoogleUserService {

	private final UserService userService;
	private final TokenService tokenService;
	private final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

	public GoogleUserService(
		UserService userService,
		TokenService tokenService) {
		this.userService = userService;
		this.tokenService = tokenService;
	}

	public Tokens getAccessToken(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("access_token", accessToken);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity request = new HttpEntity<>(parameters, headers);

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(USER_INFO_URL);
		ResponseEntity<GoogleUserInfo> responseEntity = restTemplate.exchange(
			uriComponentsBuilder.toUriString(),
			HttpMethod.GET,
			request,
			GoogleUserInfo.class
		);

		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			AuthUserInfo user = saveUser(responseEntity.getBody());
			Tokens tokens = tokenService.createTokens(user);
			return tokens;
		}
		return null;
	}

	@Transactional
	public AuthUserInfo saveUser(GoogleUserInfo googleUserInfo) {
		OAuthUserInfo oAuthUser = OAuthUserInfo.builder()
			.oauthId(googleUserInfo.id())
			.nickname(googleUserInfo.email().replace("\"", ""))
			.profileImgUrl(googleUserInfo.picture().replace("\"", ""))
			.provider("google")
			.build();
		return userService.getOrRegisterUser(oAuthUser);
	}
}
