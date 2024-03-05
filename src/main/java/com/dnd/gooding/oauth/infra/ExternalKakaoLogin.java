package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.oauth.command.application.ConnectionException;
import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.model.KakaoInfo;
import com.dnd.gooding.oauth.command.model.KakaoMember;
import com.dnd.gooding.oauth.command.model.KakaoResponse;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ExternalKakaoLogin implements ExternalLogin {

    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect-url}")
    private String redirectUrl;

    @Value("${oauth.kakao.client-secret}")
    private String clientSecret;

    @Override
    public OAuthMember getOauthToken(String code) {
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

        return getKakaoMember(Objects.requireNonNull(responseEntity.getBody()).getAccessToken());
    }

    private KakaoMember getKakaoMember(String kakaoOauthToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoOauthToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromHttpUrl(KAKAO_USER_INFO_URL);
        ResponseEntity<KakaoResponse> responseEntity =
                restTemplate.exchange(
                        uriComponentsBuilder.toUriString(), HttpMethod.GET, requestEntity, KakaoResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return new KakaoMember(
                    Objects.requireNonNull(responseEntity.getBody()).getId(),
                    Objects.requireNonNull(responseEntity.getBody()).getKakaoAccount().getEmail(),
                    Objects.requireNonNull(responseEntity.getBody()).getProperties());
        } else {
            throw new ConnectionException();
        }
    }
}
