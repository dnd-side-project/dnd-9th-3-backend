package com.dnd.gooding.domain.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.dnd.gooding.domain.file.model.File;
import com.dnd.gooding.domain.feed.model.Feed;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.global.common.model.BaseEntity;
import com.dnd.gooding.global.oauth.dto.OAuthUserInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nickname", nullable = false, length = 20)
	private String nickname;

	@Column(name = "profile_image_url", nullable = false)
	private String profileImgUrl;

	@Column(name = "provider", nullable = false)
	private String provider;

	@Column(name = "oauth_id", nullable = false)
	private String oauthId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<File> files = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)	// 주인이 아닌 쪽에 mappedBy
	private List<Record> records = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feed> feeds = new ArrayList<>();

	public static User from(OAuthUserInfo oAuthUserInfo) {
		User user = new User();
		user.nickname = oAuthUserInfo.nickname();
		user.profileImgUrl = oAuthUserInfo.profileImgUrl();
		user.provider = oAuthUserInfo.provider();
		user.oauthId = oAuthUserInfo.oauthId();
		return user;
	}
}
