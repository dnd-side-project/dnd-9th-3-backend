package com.dnd.gooding.springconfig.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dnd.gooding.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dnd.gooding.token.command.application.TokenService;
import com.dnd.gooding.token.command.model.JwtAuthenticationToken;
import com.dnd.gooding.util.ExtractUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = ExtractUtil.extractTokenFromRequest(request);
		if (accessToken != null) {
			JwtAuthenticationToken authentication = tokenService.getAuthenticationByAccessToken(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			logger.info("api call info {} => There is no valid JWT token.", request.getRequestURI());
		}
		filterChain.doFilter(request, response);
	}
}
