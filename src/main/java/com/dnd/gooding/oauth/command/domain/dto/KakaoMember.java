package com.dnd.gooding.oauth.command.domain.dto;

import java.util.Map;

public class KakaoMember extends OAuthMember {

    private String oAuthId;
    private String email;
    private Map<String, String> properties;

    public KakaoMember(String oAuthId, String email, Map<String, String> properties) {
        this.oAuthId = oAuthId;
        this.email = email;
        this.properties = properties;
    }

    @Override
    public String getImageUrl() {
        return properties.get("profile_image").replace("\"", "");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getoAuthId() {
        return oAuthId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
