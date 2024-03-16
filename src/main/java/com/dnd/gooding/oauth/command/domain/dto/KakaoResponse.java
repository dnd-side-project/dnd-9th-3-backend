package com.dnd.gooding.oauth.command.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class KakaoResponse {

    private String id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    private Map<String, String> properties;

    public KakaoResponse() {}

    public KakaoResponse(String id, KakaoAccount kakaoAccount, Map<String, String> properties) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public KakaoAccount getKakaoAccount() {
        return kakaoAccount;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
