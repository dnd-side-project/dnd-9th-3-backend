package com.dnd.gooding.springconfig.log;

import static com.dnd.gooding.springconfig.log.MDCKey.*;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

@Slf4j
public class MDCLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            setMdc(request, handlerMethod);
        }
        return true;
    }

    /** MDC에 저장된 정보를 모두 초기화 한다. */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.clear();
    }

    private void setMdc(HttpServletRequest request, HandlerMethod handlerMethod) {
        String handlerName = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        String controllerInfo = handlerName + "." + methodName;

        MDC.put(REQUEST_ID.name(), UUID.randomUUID().toString());
        MDC.put(REQUEST_IP.name(), getRemoteAddress(request));
        MDC.put(REQUEST_METHOD.name(), request.getMethod());
        MDC.put(REQUEST_URI.name(), String.valueOf(request.getRequestURL()));
        MDC.put(REQUEST_PARAMS.name(), getRequestUriWithQueryString(request));
        MDC.put(REQUEST_HANDLER.name(), controllerInfo);
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if("".equals(xForwardedFor)) {
            return request.getRemoteAddr();
        }
        String clientIp = StringUtils.substringBefore(xForwardedFor, ",");
        return StringUtils.trim(clientIp);
    }

    private String getRequestUriWithQueryString(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestURI += "?" + queryString;
        }
        return requestURI;
    }
}
