package com.dnd.gooding.util;

import javax.servlet.http.HttpServletRequest;

public class ClientUtil {

    private ClientUtil() {}

    public static String getClientIp(HttpServletRequest request) {
        try {
            String clientIp = request.getHeader("X-Forwarded-For");
            if (validationClientIp(clientIp)) clientIp = request.getHeader("Proxy-Client-IP");
            if (validationClientIp(clientIp)) clientIp = request.getHeader("WL-Proxy-Client-IP");
            if (validationClientIp(clientIp)) clientIp = request.getHeader("HTTP_CLIENT_IP");
            if (validationClientIp(clientIp)) clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (validationClientIp(clientIp)) clientIp = request.getRemoteAddr();
            return clientIp;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getClientIp() {
        try {
            String clientIp = RequestUtil.getRequest().getHeader("X-Forwarded-For");
            if (validationClientIp(clientIp))
                clientIp = RequestUtil.getRequest().getHeader("Proxy-Client-IP");
            if (validationClientIp(clientIp))
                clientIp = RequestUtil.getRequest().getHeader("WL-Proxy-Client-IP");
            if (validationClientIp(clientIp))
                clientIp = RequestUtil.getRequest().getHeader("HTTP_CLIENT_IP");
            if (validationClientIp(clientIp))
                clientIp = RequestUtil.getRequest().getHeader("HTTP_X_FORWARDED_FOR");
            if (validationClientIp(clientIp)) clientIp = RequestUtil.getRequest().getRemoteAddr();
            return clientIp;
        } catch (Exception e) {
            return "";
        }
    }

    private static boolean validationClientIp(String clientIp) {
        return (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp));
    }
}
