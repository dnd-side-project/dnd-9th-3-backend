package com.dnd.gooding.domain.user.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.dnd.gooding.domain.file.infrastructure.FileEntity;
import com.dnd.gooding.domain.onboard.infrastructure.OnboardEntity;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.domain.BaseEntity;
import com.dnd.gooding.global.oauth.domain.OAuthUser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

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

	@Column(name = "onboard_yn", length = 5)
	private String onboardYn;

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	private List<OnboardEntity> onboards = new ArrayList<>();

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	private List<FileEntity> files = new ArrayList<>();
	//
	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)	// 주인이 아닌 쪽에 mappedBy
	// private List<Record> records = new ArrayList<>();
	//
	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	// private List<Feed> feeds = new ArrayList<>();

	public static UserEntity from(User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.id = user.getId();
		userEntity.nickname = user.getNickname();
		userEntity.profileImgUrl = user.getProfileImgUrl();
		userEntity.provider = user.getProvider();
		userEntity.oauthId = user.getOauthId();
		userEntity.onboardYn = user.getOnboardYn();
		userEntity.onboards = user.getOnboards().stream().map(OnboardEntity::from).collect(Collectors.toList());
		return userEntity;
	}

	public static UserEntity from(OAuthUser oAuthUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.id = oAuthUser.id();
		userEntity.nickname = oAuthUser.nickname();
		userEntity.profileImgUrl = oAuthUser.profileImgUrl();
		userEntity.provider = oAuthUser.provider();
		userEntity.oauthId = oAuthUser.oauthId();
		return userEntity;
	}

	public User toModel() {
		return User.builder()
			.id(id)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.provider(provider)
			.oauthId(oauthId)
			.onboardYn(onboardYn)
			.onboards(onboards.stream().map(OnboardEntity::toModel).collect(Collectors.toList()))
			.build();
	}
}
