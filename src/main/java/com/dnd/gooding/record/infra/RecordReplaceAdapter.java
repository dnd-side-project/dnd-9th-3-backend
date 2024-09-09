package com.dnd.gooding.record.infra;

import com.dnd.gooding.record.command.application.out.RecordReplacePort;
import com.dnd.gooding.record.command.dto.RecordPlace;
import com.dnd.gooding.record.infra.dto.response.KakaoPlaceResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

    private static final String KAKAO_REPLACE_INFO_URL =
            "https://dapi.kakao.com/v2/local/search/keyword.json";

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Override
    public List<RecordPlace> getPlaces(String keyword, int page, int size) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + clientId);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromHttpUrl(KAKAO_REPLACE_INFO_URL)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("query", keyword);

        ResponseEntity<KakaoPlaceResponse> response =
                restTemplate.exchange(
                        uriBuilder.build(false).toUriString(),
                        HttpMethod.GET,
                        entity,
                        KakaoPlaceResponse.class);

        Objects.requireNonNull(response.getBody(), "키워드 검색 정보를 가져올 수 없습니다.");

        return response.getBody().getDocuments().stream()
                .map(RecordPlace::from)
                .collect(Collectors.toList());
    }
}
