package com.dnd.gooding.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestUtil {

	public static HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getRequestURL() {
		return getRequest().getRequestURL().toString();
	}

	public static String getHost() {
		HttpServletRequest request = getRequest();
		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
		UriComponents uriComponents = UriComponentsBuilder.fromHttpRequest(servletServerHttpRequest).build();
		String scheme = uriComponents.getScheme();
		String serverName = uriComponents.getHost();
		int serverPort = (uriComponents.getPort() == -1) ? request.getServerPort() : uriComponents.getPort();
		String contextPath = request.getContextPath();
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://");
		url.append(serverName);
		if (serverPort != -1 && serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		return url.toString();
	}
}
