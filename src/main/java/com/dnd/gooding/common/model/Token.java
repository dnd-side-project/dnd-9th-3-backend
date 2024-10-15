package com.dnd.gooding.common.model;

import lombok.Getter;

@Getter
public class Token {

    private String memberId;
    private String accessToken;
    private String refreshToken;

    public Token(String memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
