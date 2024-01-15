package com.dnd.gooding.oauth.command.application;

import java.util.Objects;

import com.dnd.gooding.oauth.command.domain.CreateOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.token.command.application.TokenService;
import com.dnd.gooding.oauth.command.domain.OAuth;
import com.dnd.gooding.oauth.command.model.KakaoInfo;
import com.dnd.gooding.oauth.command.model.KakaoMember;
import com.dnd.gooding.oauth.command.model.KakaoResponse;

@Service
public class KakaoLoginService {

	private final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
	private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
	@Value("${oauth.kakao.client-id}")
	private String clientId;
	@Value("${oauth.kakao.redirect-url}")
	private String redirectUrl;
	@Value("${oauth.kakao.client-secret}")
	private String clientSecret;

	@Autowired
	private TokenService tokenService;
	@Autowired
	private CreateOAuthService createOAuthService;

	public Token getAccessToken(String code) {
		String kakaoOauthToken = getKakaoOauthToken(code);
		return getToken(kakaoOauthToken);
	}

	private String getKakaoOauthToken(String code) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUrl);
		params.add("code", code);
		params.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<KakaoInfo> responseEntity =
			restTemplate.postForEntity(KAKAO_TOKEN_URL, request, KakaoInfo.class);
		return Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
	}

	private Token getToken(String kakaoOauthToken) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + kakaoOauthToken);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_USER_INFO_URL);
		ResponseEntity<KakaoResponse> responseEntity = restTemplate.exchange(
			uriComponentsBuilder.toUriString(),
			HttpMethod.GET,
			requestEntity,
			KakaoResponse.class
		);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			KakaoMember kakaoMember = new KakaoMember(
				Objects.requireNonNull(responseEntity.getBody()).getId(),
				responseEntity.getBody().getProperties());
			OAuth oAuth = new OAuth(kakaoMember.getOauthId(), kakaoMember.getImageUrl(),
				kakaoMember.getProvider());

			createOAuthService.createOAuth(oAuth);
			Token token = tokenService.createTokens(oAuth);
			return new Token(kakaoMember.getOauthId(), token.getAccessToken(), token.getRefreshToken());
		} else {
			throw new KakaoConnectionException();
		}
	}
}
