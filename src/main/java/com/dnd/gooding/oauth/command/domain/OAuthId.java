package com.dnd.gooding.oauth.command.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OAuthId implements Serializable {

  @Column(name = "oauth_id")
  private String id;

  protected OAuthId() {}

  public OAuthId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public static OAuthId of(String id) {
    return new OAuthId(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OAuthId oAuthId = (OAuthId) o;
    return Objects.equals(id, oAuthId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
