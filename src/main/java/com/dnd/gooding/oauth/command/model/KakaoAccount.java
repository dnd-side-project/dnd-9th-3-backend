package com.dnd.gooding.oauth.command.model;

public class KakaoAccount {

  private String email;

  public KakaoAccount() {}

  public KakaoAccount(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
