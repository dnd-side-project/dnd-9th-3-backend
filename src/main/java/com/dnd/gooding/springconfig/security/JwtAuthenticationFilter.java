package com.dnd.gooding.springconfig.security;

import com.dnd.gooding.token.command.domain.service.TokenService;
import com.dnd.gooding.token.command.domain.dto.JwtAuthenticationToken;
import com.dnd.gooding.util.ExtractUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = ExtractUtil.extractTokenFromRequest(request);
        if (accessToken != null) {
            JwtAuthenticationToken authentication =
                    tokenService.getAuthenticationByAccessToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.info("api call info {} => There is no valid JWT token.", request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
