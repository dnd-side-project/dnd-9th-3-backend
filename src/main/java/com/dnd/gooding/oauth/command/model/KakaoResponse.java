package com.dnd.gooding.oauth.command.model;

import java.util.Map;

public class KakaoResponse {

  private String id;
  private KakaoAccount kakao_account;
  private Map<String, String> properties;

  public KakaoResponse() {}

  public KakaoResponse(String id, KakaoAccount kakao_account, Map<String, String> properties) {
    this.id = id;
    this.kakao_account = kakao_account;
    this.properties = properties;
  }

  public String getId() {
    return id;
  }

  public KakaoAccount getKakao_account() {
    return kakao_account;
  }

  public Map<String, String> getProperties() {
    return properties;
  }
}
