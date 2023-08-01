package com.dnd.gooding.domain.user.model;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd.gooding.global.common.domain.BaseEntity;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "nickname", nullable = false, length = 20)
	private String nickname;

	@Column(name = "profile_image_url", nullable = false)
	private String profileImgUrl;

	@Column(name = "provider", nullable = false)
	private String provider;

	@Column(name = "oauth_id", nullable = false)
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
