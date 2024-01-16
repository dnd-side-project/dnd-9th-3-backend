package com.dnd.gooding.springconfig.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dnd.gooding.token.command.application.TokenService;
import com.dnd.gooding.token.command.domain.JwtAuthenticationToken;
import com.dnd.gooding.util.ExtractUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = ExtractUtil.extractTokenFromRequest(request);
		if (accessToken != null) {
			JwtAuthenticationToken authentication = tokenService.getAuthenticationByAccessToken(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}
