package com.dnd.gooding.token.command.model;

public class JwtAuthentication {

  private String id;
  private String accessToken;

  public JwtAuthentication() {}

  public JwtAuthentication(String id, String accessToken) {
    this.id = id;
    this.accessToken = accessToken;
  }

  public String getId() {
    return id;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
