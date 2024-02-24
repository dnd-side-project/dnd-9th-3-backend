package com.dnd.gooding.token.command.application;

import com.dnd.gooding.common.model.Token;
import com.dnd.gooding.token.command.domain.JwtTokenProvider;
import com.dnd.gooding.token.command.domain.RefreshToken;
import com.dnd.gooding.token.command.domain.RefreshTokenRepository;
import com.dnd.gooding.token.command.model.JwtAuthentication;
import com.dnd.gooding.token.command.model.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.expiry-seconds.refresh-token:36000}")
    private int refreshTokenExpirySeconds;

    public TokenService(
            JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public Token createTokens(String id) {
        String accessToken = createAccessToken(id);
        String refreshToken = createRefreshToken(id);
        return new Token(id, accessToken, refreshToken);
    }

    public JwtAuthenticationToken getAuthenticationByAccessToken(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);
        Claims claims = jwtTokenProvider.getClaims(accessToken);
        String id = claims.get("id", String.class);
        JwtAuthentication principal = new JwtAuthentication(id, accessToken);
        return new JwtAuthenticationToken(principal, null);
    }

    private String createAccessToken(String id) {
        return jwtTokenProvider.createAccessToken(id);
    }

    @Transactional
    public String createRefreshToken(String id) {
        String uuid = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(uuid, id, refreshTokenExpirySeconds);
        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Transactional
    public String getAccessTokensByRefreshToken(String refreshToken) {
        return refreshTokenRepository
                .findById(refreshToken)
                .map(token -> createAccessToken(token.getId()))
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findById(refreshToken).ifPresent(refreshTokenRepository::delete);
    }
}
