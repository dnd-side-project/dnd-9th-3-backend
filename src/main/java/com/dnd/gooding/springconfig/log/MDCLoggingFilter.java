package com.dnd.gooding.springconfig.log;

import static com.dnd.gooding.springconfig.log.MDCKey.*;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.dnd.gooding.util.ClientUtil;
import com.dnd.gooding.util.RequestUtil;

@Component
public class MDCLoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		try {
			setMdc(request, response);
			chain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}

	private void setMdc(ServletRequest request, ServletResponse response) {
		MDC.put(REQUEST_ID.name(), UUID.randomUUID().toString());
		MDC.put(REQUEST_IP.name(), ClientUtil.getClientIp((HttpServletRequest)request));
		MDC.put(REQUEST_URI.name(), RequestUtil.getRequestURL());
	}
}
