package com.dnd.gooding.oauth.command.domain;

import com.dnd.gooding.oauth.command.model.OAuthMember;

public interface ExternalLogin {

    OAuthMember getOauthToken(String code);
}
