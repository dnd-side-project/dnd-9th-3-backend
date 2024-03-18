package com.dnd.gooding.oauth.command.application.out;

import com.dnd.gooding.oauth.command.domain.dto.OAuthMember;

public interface ExternalLoginPort {

    OAuthMember getOauthToken(String code);
}
