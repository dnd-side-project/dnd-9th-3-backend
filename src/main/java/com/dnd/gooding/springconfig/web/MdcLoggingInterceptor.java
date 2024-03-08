package com.dnd.gooding.springconfig.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MdcLoggingInterceptor implements HandlerInterceptor {

    public static final String REQUEST_CONTROLLER_MDC_KEY = "handler";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            String handlerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            String controllerInfo = handlerName + "." + methodName;
            MDC.put(REQUEST_CONTROLLER_MDC_KEY, controllerInfo);
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
