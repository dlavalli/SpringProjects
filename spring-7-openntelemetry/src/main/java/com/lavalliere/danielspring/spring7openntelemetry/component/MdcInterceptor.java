package com.lavalliere.danielspring.spring7openntelemetry.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
 * If you need access to Spring MVC-specific metadata (like discovering which
 * specific controller method or Java annotation is about to be invoked via HandlerMethod),
 * you can use a HandlerInterceptor.
 * - Setup: Use preHandle() to fill the MDC map.
 * - Cleanup: Always use afterCompletion() to purge the MDC map. Do not use postHandle(),
 *   because if your controller throws an exception, postHandle() is skipped completely,
 *   resulting in an MDC leak
 *
 *
 * NOTE: If you use an interceptor, you must explicitly register it
 *       by implementing WebMvcConfigurer and overriding addInterceptors()
 */

// @Component
public class MdcInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put("userId", request.getRemoteUser());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Guaranteed to run even if an exception occurs in the controller
        MDC.clear();
    }
}
