package com.dnd.gooding.springconfig.log;

import static com.dnd.gooding.springconfig.log.MDCKey.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MDCLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            setMdc(request, handlerMethod);
        }
        return true;
    }

    /** MDC에 저장된 정보를 모두 초기화 한다. */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {}

    private void setMdc(HttpServletRequest request, HandlerMethod handlerMethod) {
        String handlerName = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        String controllerInfo = handlerName + "." + methodName;

        MDC.put(REQUEST_METHOD.name(), request.getMethod());
        MDC.put(REQUEST_HANDLER.name(), controllerInfo);
    }
}
