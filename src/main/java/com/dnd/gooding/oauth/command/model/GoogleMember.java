package com.dnd.gooding.oauth.command.model;

public class GoogleMember extends OAuthMember {

  private String sub;
  private String name;
  private String picture;

  public GoogleMember(String sub, String name, String picture) {
    this.sub = sub.replace("\"", "");
    this.name = name.replace("\"", "");
    this.picture = picture.replace("\"", "");
  }

  @Override
  public String getoAuthId() {
    return sub;
  }

  @Override
  public String getImageUrl() {
    return picture;
  }

  @Override
  public String getProvider() {
    return "google";
  }

  public String getSub() {
    return sub;
  }

  public String getName() {
    return name;
  }

  public String getPicture() {
    return picture;
  }
}
