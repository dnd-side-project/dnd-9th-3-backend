package com.dnd.gooding.token.command.domain;

import java.util.Objects;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
public class RefreshToken {

	@Id
	private String refreshToken;
	private String oauthId;
	private String userRole;
	@TimeToLive
	private long expiration;

	protected RefreshToken() {
	}

	public RefreshToken(String refreshToken, String oauthId, String useRole, long expiration) {
		this.refreshToken = checkRefreshToken(refreshToken);
		this.oauthId = checkUserId(oauthId);
		this.userRole = checkRole(useRole);
		this.expiration = checkExpiration(expiration);
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getOauthId() {
		return oauthId;
	}

	public String getUserRole() {
		return userRole;
	}

	public long getExpiration() {
		return expiration;
	}

	private String checkRefreshToken(String refreshToken) {
		if (!Objects.nonNull(refreshToken) || refreshToken.isBlank()) {
			throw new IllegalArgumentException("올바르지 않은 토큰 값");
		}
		return refreshToken;
	}

	private String checkUserId(String oauthId) {
		if (!Objects.nonNull(oauthId) || oauthId.isBlank()) {
			throw new IllegalArgumentException("올바르지 않은 oauthId");
		}
		return oauthId;
	}

	private String checkRole(String role) {
		if (!Objects.nonNull(role) || role.isBlank()) {
			throw new IllegalArgumentException("올바르지 않은 권한");
		}
		return role;
	}

	private long checkExpiration(long expiration) {
		if (expiration < 1) {
			throw new IllegalArgumentException("올바르지 않은 만료 시간");
		}
		return expiration;
	}
}
