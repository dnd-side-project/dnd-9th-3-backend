package com.dnd.gooding.domain.user.model;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd.gooding.global.domain.BaseEntity;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String nickname;

	@Column(nullable = false)
	private String profileImgUrl;

	@Column(nullable = false)
	private String provider;

	@Column(nullable = false)
	private String oauthId;

	public static User from(OAuthUserInfo oAuthUserInfo) {
		User user = new User();
		user.nickname = oAuthUserInfo.nickname();
		user.profileImgUrl = oAuthUserInfo.profileImgUrl();
		user.provider = oAuthUserInfo.provider();
		user.oauthId = oAuthUserInfo.oauthId();
		return user;
	}

}
