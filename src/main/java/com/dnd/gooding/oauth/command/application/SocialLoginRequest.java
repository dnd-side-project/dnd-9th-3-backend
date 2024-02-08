package com.dnd.gooding.oauth.command.application;

public class SocialLoginRequest {

    private String code;
    private String memberId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
