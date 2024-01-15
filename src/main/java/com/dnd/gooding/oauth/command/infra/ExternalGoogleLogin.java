package com.dnd.gooding.oauth.command.infra;

import com.dnd.gooding.oauth.command.domain.ExternalLogin;
import com.dnd.gooding.oauth.command.model.OAuthMember;

public class ExternalGoogleLogin implements ExternalLogin {
	
	@Override
	public OAuthMember getOauthToken(String code) {
		return null;
	}
}
