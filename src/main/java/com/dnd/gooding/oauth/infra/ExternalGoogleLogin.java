package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.oauth.command.application.ConnectionException;
import com.dnd.gooding.oauth.command.model.GoogleMember;
import com.dnd.gooding.oauth.command.model.OAuthMember;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ExternalGoogleLogin {

    private static final String USER_INFO_URL = "https://oauth2.googleapis.com/tokeninfo";

    public OAuthMember getOauthToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(headers);

        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromHttpUrl(USER_INFO_URL).queryParam("id_token", code);

        ResponseEntity<GoogleMember> responseEntity =
                restTemplate.exchange(
                        uriComponentsBuilder.toUriString(), HttpMethod.GET, request, GoogleMember.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new ConnectionException();
        }
    }
}
