package com.dnd.gooding.oauth.command.model;

public class OAuthMember {

    private String oAuthId;
    private String email;
    private String imageUrl;
    private String provider;

    public OAuthMember() {}

    public OAuthMember(String oAuthId, String email, String imageUrl, String provider) {
        this.oAuthId = oAuthId;
        this.email = email;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }

    public String getoAuthId() {
        return oAuthId;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProvider() {
        return provider;
    }
}
