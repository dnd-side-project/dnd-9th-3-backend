package com.dnd.gooding.token.command.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
public class RefreshToken {

	@Id
	private String refreshToken;
	private String oauthId;
	@TimeToLive
	private long expiration;

	protected RefreshToken() {
	}

	public RefreshToken(String refreshToken, String oauthId, long expiration) {
		this.refreshToken = checkRefreshToken(refreshToken);
		this.oauthId = checkUserId(oauthId);
		this.expiration = checkExpiration(expiration);
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getOauthId() {
		return oauthId;
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

	private long checkExpiration(long expiration) {
		if (expiration < 1) {
			throw new IllegalArgumentException("올바르지 않은 만료 시간");
		}
		return expiration;
	}
}
