package com.dnd.gooding.record.infra;

import com.dnd.gooding.oauth.command.domain.dto.KakaoResponse;
import com.dnd.gooding.record.command.application.out.RecordReplacePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RecordReplaceAdapter implements RecordReplacePort {

    private static final String KAKAO_REPLACE_INFO_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Override
    public void getPlaces(String keyword) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + clientId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromHttpUrl(KAKAO_REPLACE_INFO_URL)
                        .queryParam("query", keyword);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                        uriComponentsBuilder.toUriString(), HttpMethod.GET, requestEntity, String.class);

        System.out.println(responseEntity);
    }
}
