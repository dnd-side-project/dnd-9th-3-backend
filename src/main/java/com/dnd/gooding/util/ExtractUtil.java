package com.dnd.gooding.util;

import static org.springframework.http.HttpHeaders.*;

import javax.servlet.http.HttpServletRequest;

public class ExtractUtil {

	private static final String BEARER_TYPE = "Bearer";

	public ExtractUtil() {
	}

	public static String extractTokenFromRequest(HttpServletRequest request) {
		String authHeaderValue = request.getHeader(AUTHORIZATION);
		if (authHeaderValue != null) {
			return extractToken(authHeaderValue);
		}
		return null;
	}

	public static String extractToken(String authHeaderValue) {
		if (authHeaderValue.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
			return authHeaderValue.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}
}
