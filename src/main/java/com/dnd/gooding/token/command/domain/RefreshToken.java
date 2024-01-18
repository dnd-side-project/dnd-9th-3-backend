package com.dnd.gooding.token.command.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
public class RefreshToken {

	@Id
	private String refreshToken;
	private String id;
	@TimeToLive
	private long expiration;

	protected RefreshToken() {
	}

	public RefreshToken(String refreshToken, String id, long expiration) {
		this.refreshToken = checkRefreshToken(refreshToken);
		this.id = checkId(id);
		this.expiration = checkExpiration(expiration);
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getId() {
		return id;
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

	private String checkId(String id) {
		if (!Objects.nonNull(id) || id.isBlank()) {
			throw new IllegalArgumentException("올바르지 않은 memberId");
		}
		return id;
	}

	private long checkExpiration(long expiration) {
		if (expiration < 1) {
			throw new IllegalArgumentException("올바르지 않은 만료 시간");
		}
		return expiration;
	}
}
