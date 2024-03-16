package com.dnd.gooding.springconfig.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MDCLoggingInterceptor implements HandlerInterceptor {

    public static final String REQUEST_CONTROLLER_MDC_KEY = "handler";
    private static final String REQUEST_URL = "requestUrl";
    private static final String REMOTE_ADDR = "remoteAddr";
    private static final String REQUEST_METHOD = "requestMethod";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            String handlerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            String controllerInfo = handlerName + "." + methodName;
            MDC.put(REQUEST_CONTROLLER_MDC_KEY, controllerInfo);
            MDC.put(REMOTE_ADDR, request.getRemoteAddr());
            MDC.put(REQUEST_METHOD, request.getMethod());
            MDC.put(REQUEST_URL, String.valueOf(request.getRequestURL()));
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
}
