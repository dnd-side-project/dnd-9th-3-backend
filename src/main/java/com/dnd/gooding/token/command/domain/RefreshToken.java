package com.dnd.gooding.token.command.domain;

import java.util.Objects;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id private String token;
    private String id;
    @TimeToLive private long expiration;

    protected RefreshToken() {}

    public RefreshToken(String token, String id, long expiration) {
        this.token = checkRefreshToken(token);
        this.id = checkId(id);
        this.expiration = checkExpiration(expiration);
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
