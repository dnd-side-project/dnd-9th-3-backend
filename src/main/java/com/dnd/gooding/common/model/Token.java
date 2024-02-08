package com.dnd.gooding.common.model;

public class Token {

    private String memberId;
    private String accessToken;
    private String refreshToken;

    public Token(String memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
